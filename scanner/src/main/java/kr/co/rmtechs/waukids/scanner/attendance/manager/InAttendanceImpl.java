package kr.co.rmtechs.waukids.scanner.attendance.manager;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;
import kr.co.rmtechs.waukids.scanner.state.bean.StateVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("inAttendanceManager")
public class InAttendanceImpl extends AbstractAttendance {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void attend(final StudentVO student) {
        if ((student == null) || (student.getAttend() == StudentVO.STATE_ATTENDANCE)
                || (student.getAttend() == StudentVO.STATE_LEAVE)) {
            return;
        }

        insertDB(student);
        insertState(student);
        updateBattery(student);
    }

    private void insertDB(final StudentVO student) {
        try {
            student.setAttend(StudentVO.STATE_ATTENDANCE);
            student.setAttendCount(0);

            studentService.insert(student);

            logger.info("출석 : " + student);
        }
        catch (final Exception e) {
            logger.error("출석 error : " + e);
        }
    }

    private void insertState(final StudentVO student) {
        final StateVO state = new StateVO();

        state.setSchoolId(student.getSchoolId());
        state.setStudentId(student.getStudentId());

        state.setCode(20030);
        state.setMessage("자녀가 등원하였습니다.");

        studentService.insertState(state);
    }

    private void updateBattery(final StudentVO student) {
        if (student.getBattery() != -1) {
            studentService.updateBattery(student);
        }
    }
}
