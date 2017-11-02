package kr.co.rmtechs.waukids.scanner.attendance.manager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("inKindergartenManager")
public class InKindergartenImpl implements KindergartenManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired()
    @Resource(name = "inAttendanceManager")
    private KindergartenManager attendanceManager;

    @Autowired()
    @Resource(name = "inAbsenceManager")
    private KindergartenManager absenceManager;

    @Override
    public void initialize() {
        logger.info("InSchool Manager initialize.");

        attendanceManager.initialize();
        absenceManager.initialize();
    }

    @Override
    public void shutdown() {
        logger.info("InSchool Manager stop.");

        attendanceManager.shutdown();
        absenceManager.shutdown();
    }
}
