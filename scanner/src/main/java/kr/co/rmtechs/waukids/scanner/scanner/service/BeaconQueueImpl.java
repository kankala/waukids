package kr.co.rmtechs.waukids.scanner.scanner.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.co.rmtechs.waukids.scanner.scanner.beacon.Beacon;

@Repository("beaconQueue")
public class BeaconQueueImpl implements BeaconQueue<Beacon> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private BlockingQueue<Beacon> queue;

    @PostConstruct
    public void init() {
        queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void publish(final Beacon beacon) {
        try {
            queue.put(beacon);

            logger.debug("publish : " + beacon.getMacAddress());
        } catch (final InterruptedException e) {
            logger.error("publish error : " + e);
        }
    }

    @Override
    public Beacon take() throws InterruptedException {
        return queue.take();
    }

    @Override
    @PreDestroy
    public void clear() {
        queue.clear();

        logger.info("queue clear.");
    }
}
