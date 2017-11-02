package kr.co.rmtechs.waukids.scanner.attendance.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;
import kr.co.rmtechs.waukids.scanner.attendance.service.StudentGroup;
import kr.co.rmtechs.waukids.scanner.attendance.service.StudentService;
import kr.co.rmtechs.waukids.scanner.scanner.beacon.Beacon;
import kr.co.rmtechs.waukids.scanner.scanner.service.BeaconQueue;

public abstract class AbstractAttendance implements KindergartenManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${attendance.maxThread:200}")
    private int MAX_THREAD;

    @Value("${scanner.interval:10}")
    private int SCANNER_INTERVAL;

    private ExecutorService executor;

    @Override
    public void initialize() {
        logger.info("consumer start.");

        executor = Executors.newWorkStealingPool(MAX_THREAD);

        for (int i = 0; i < MAX_THREAD; i++) {
            executor.execute(new BeaconConsumer());
        }
    }

    @Autowired
    private BeaconQueue<Beacon> queue;

    @Autowired
    private StudentGroup groups;

    @Autowired
    @Resource(name = "studentService")
    protected StudentService studentService;

    private class BeaconConsumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final Beacon beacon = queue.take();

                    final StudentVO student = groups.get(beacon.getMacAddress());

                    student.setBattery(beacon.getBattery());

                    attend(student);

                    logger.info("Consumer : " + beacon.getMacAddress() + ", student : " + student);
                } catch (final InterruptedException e) {
                    logger.info("queue interrupt close.");

                    break;
                }
            }
        }
    }

    public abstract void attend(StudentVO student);

    @Override
    public void shutdown() {
        // queue.clear();

        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();

            logger.info("shutdown.");
        }
    }
}
