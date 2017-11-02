package kr.co.rmtechs.waukids.scanner.scanner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;
import kr.co.rmtechs.waukids.scanner.attendance.service.StudentGroup;
import kr.co.rmtechs.waukids.scanner.scanner.beacon.Beacon;
import kr.co.rmtechs.waukids.scanner.scanner.beacon.Scanner;
import kr.co.rmtechs.waukids.scanner.scanner.service.BeaconQueue;
import kr.co.rmtechs.waukids.scanner.scanner.service.ScannerServer;

@Service("scannerManager")
public class ScannerManagerImpl implements ScannerManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final byte PACKET_END = (byte) 0xFE;

    @Autowired
    private ScannerServer server;

    @Autowired
    private BeaconQueue<Beacon> beaconQueue;

    @Autowired
    private StudentGroup groups;

    private BlockingQueue<Byte> byteQueue;

    private ConcurrentMap<String, Beacon> beaconMaps;

    private Executor executor;

    @PostConstruct
    public void init() {
        beaconMaps = new ConcurrentHashMap<>();
        byteQueue = new LinkedBlockingQueue<>();

        server.init();

        executor = Executors.newSingleThreadExecutor();

        final Runnable task = new Runnable() {
            @Override
            public void run() {
                takeQueue();
            }
        };

        executor.execute(task);

    }

    private void put(final String macAddress, final Beacon beacon) {
        final StudentVO student = groups.get(beacon.getMacAddress());

        if (student != null) {
            beaconMaps.putIfAbsent(macAddress, beacon);
        }
    }

    @Override
    public void put(final byte[] bytes) {
        for (final byte b : bytes) {
            byteQueue.add(b);
        }

        // logger.debug("put(byte) : " + bytes.length + ", queue : " +
        // byteQueue.size());
    }

    private static final int MAX_BUF = 1024;

    private void takeQueue() {
        final byte[] bytes = new byte[MAX_BUF];
        int position = 0;

        while (true) {
            byte b;
            try {
                b = byteQueue.take();

                if (b == PACKET_END) {
                    // logger.debug("before parse. " +
                    // ByteUtil.toHexString(bytes));

                    if ((bytes[0] != Scanner.SYNC) || (bytes[5] != Scanner.CMD_SCAN)) {
                        // logger.error("wrong sync byte. reset position");

                        position = 0;
                        continue;
                    }

                    final Beacon beacon = Scanner.parse(bytes);

                    // logger.debug(("parse: " + beacon) != null ?
                    // beacon.getMacAddress() : "null");

                    if (beacon != null) {
                        put(beacon.getMacAddress(), beacon);

                        // logger.debug("buff: " + beacon.getMacAddress() + ",
                        // battery : " + beacon.getBattery());
                    }

                    position = 0;
                }
                else if (position >= MAX_BUF) {
                    logger.debug("max buffer.");

                    position = 0;
                }
                else {
                    bytes[position++] = b;
                }

                // logger.debug("takeQueue : " + byteQueue.size() + ", " +
                // ByteUtil.toHexString(bytes));
            } catch (final InterruptedException e) {
                logger.error("Byte Queue error : " + e);
            }
        }
    }

    public void publish() {
        for (final String key : beaconMaps.keySet()) {
            final Beacon beacon = beaconMaps.remove(key);

            // logger.info("scanner publish : " + beacon.getMacAddress());

            beaconQueue.publish(beacon);
        }
    }
}
