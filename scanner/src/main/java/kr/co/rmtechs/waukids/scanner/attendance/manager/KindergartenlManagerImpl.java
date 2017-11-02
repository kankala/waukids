package kr.co.rmtechs.waukids.scanner.attendance.manager;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("kindergartenManager")
public class KindergartenlManagerImpl {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${school.in.endTime:13}")
    private int IN_END_TIME;

    @Value("${school.out.endTime:23}")
    private int OUT_END_TIME;

    @Autowired
    @Resource(name = "inKindergartenManager")
    private KindergartenManager inSchoolManager;

    @Autowired
    @Resource(name = "outKindergartenManager")
    private KindergartenManager outSchoolManager;

    @PostConstruct
    public void init() {
        int currentTime = LocalDateTime.now().getHour();

        if (currentTime < IN_END_TIME) {
            enterKindergarten();
        } else if (currentTime >= IN_END_TIME && currentTime < OUT_END_TIME) {
            leaveKindergarten();
        }
    }

    public void enterKindergarten() {
        logger.info("enterKindergarten Manager initialize.");

        outSchoolManager.shutdown();
        inSchoolManager.initialize();
    }

    public void leaveKindergarten() {
        logger.info("leaveKindergarten Manager initialize.");

        inSchoolManager.shutdown();
        outSchoolManager.initialize();
    }

    public void destroy() {
        logger.info("Kindergarten Manager stop.");

        inSchoolManager.shutdown();
        outSchoolManager.shutdown();
    }
}
