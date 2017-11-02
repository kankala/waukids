package kr.co.rmtechs.waukids.scanner.scanner.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.rmtechs.waukids.scanner.scanner.ScannerManager;
import kr.co.rmtechs.waukids.scanner.scanner.beacon.Scanner;
import kr.co.rmtechs.waukids.scanner.util.ByteUtil;

@Service("scannerServer")
public class ScannerServerImpl implements ScannerServer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int MAX_BUF = 8192;

    @Value("${scanner.port:10000}")
    private int DEFAULT_PORT;

    @Value("${scanner.maxThread:200}")
    private int MAX_THREAD;

    @Value("${scanner.heartbeat:4}")
    private int HEART_BEAT_PERIOD;

    private Selector selector;
    private Executor executor;

    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    private ScannerManager manager;

    @Override
    public void init() {
        initServer();

        initSchedule();
    }

    private void initServer() {
        executor = Executors.newWorkStealingPool(MAX_THREAD);

        final Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    startServer();
                } catch (final IOException e) {
                    logger.error("start server : " + e);
                }
            }
        };

        executor.execute(task);
    }

    private void startServer() throws IOException, ClosedChannelException {
        selector = Selector.open();

        try (ServerSocketChannel server = ServerSocketChannel.open()) {

            server.configureBlocking(false);
            server.setOption(StandardSocketOptions.SO_REUSEADDR, true);

            server.socket().bind(new InetSocketAddress(DEFAULT_PORT));

            server.register(selector, SelectionKey.OP_ACCEPT);

            logger.info("Start scanner server port " + DEFAULT_PORT);

            while (true) {
                selector.select();

                final Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    final SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        accept(key, selector);
                    }
                    if (key.isReadable()) {
                        read(key);
                    }
                }
            }
        } catch (final IOException e) {
            logger.error("socket : " + e);
        } finally {
            selector.close();
        }
    }

    private void accept(final SelectionKey key, final Selector selector) throws IOException, ClosedChannelException {
        final ServerSocketChannel server = (ServerSocketChannel) key.channel();
        final SocketChannel client = server.accept();

        logger.info("Incoming connection from: " + getClientIpAddress(client) + ", ");

        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    private void read(final SelectionKey key) {
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                readBuffer(key);
            }
        };

        executor.execute(task);
    }

    private final ByteBuffer buffer = ByteBuffer.allocate(MAX_BUF);

    private void readBuffer(final SelectionKey key) {
        lock.lock();

        final SocketChannel client = (SocketChannel) key.channel();

        try {
            buffer.clear();

            final int length = client.read(buffer);

            if (length == 0) {
                return;
            }
            else if (length == -1) {
                client.close();
                key.cancel();

                logger.info("client close. : " + getClientIpAddress(client));

                return;
            }

            final byte[] bytes = new byte[length];
            System.arraycopy(buffer.array(), 0, bytes, 0, length);

            // logger.debug("byte " + ByteUtil.toHexString(bytes) + ", " +
            // getClientIpAddress(client));

            manager.put(bytes);
        } catch (final Exception e) {
            try {
                if (client.isConnected()) {
                    client.close();
                }
            } catch (final IOException e1) {
            }

            logger.error("socket read error : " + e);
        } finally {
            buffer.clear();

            lock.unlock();
        }
    }

    private String getClientIpAddress(final SocketChannel client) throws IOException {
        final InetSocketAddress address = (InetSocketAddress) client.getRemoteAddress();

        return address.getAddress().getHostAddress();
    }

    private void initSchedule() {
        final ScheduledExecutorService exec = new ScheduledThreadPoolExecutor(1);

        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    broadcast();
                } catch (final Exception e) {
                    logger.error("broadcast : " + e);
                }
            }
        }, 1, HEART_BEAT_PERIOD, TimeUnit.MINUTES);
    }

    private void broadcast() throws IOException {
        if (!selector.isOpen()) {
            return;
        }

        final byte[] bytes = Scanner.getStatusMessage();
        final ByteBuffer buffer = ByteBuffer.allocate(bytes.length);

        buffer.put(bytes);
        buffer.flip();

        for (final SelectionKey key : selector.keys()) {
            if (key.isValid() && (key.channel() instanceof SocketChannel)) {
                final SocketChannel client = (SocketChannel) key.channel();

                client.write(buffer);

                buffer.rewind();

                logger.info("heartbeat : " + ByteUtil.toHexString(bytes) + ", " + getClientIpAddress(client));
            }
        }
    }
}
