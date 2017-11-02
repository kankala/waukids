package kr.co.rmtechs.waukids.scanner.attendance.job;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;
import kr.co.rmtechs.waukids.scanner.state.bean.StateVO;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutAbsenceJob extends AbstractAbsenceJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void absence() {
        final Collection<StudentVO> students = groups.getStudentsInStay(schoolId);

        for (final StudentVO student : students) {
            if (student.getAttend() == StudentVO.STATE_LEAVE) {
                continue;
            }

            if (student.isStay()) {
                student.setStay(false);
                student.setAttendCount(0);
            }
            else {
                int attendCount = student.getAttendCount();

                if ((attendCount >= repeat) && (student.getAttend() == StudentVO.STATE_ATTENDANCE)) {
                    student.setAttend(StudentVO.STATE_LEAVE);

                    insertState(student);

                    logger.info("하원 : " + student.toString());
                }

                student.setAttendCount(++attendCount);
            }
        }
    }

    private void insertState(final StudentVO student) {
        final StateVO state = new StateVO();

        state.setSchoolId(student.getSchoolId());
        state.setStudentId(student.getStudentId());

        state.setCode(20040);
        state.setMessage("자녀가 하원하였습니다.");

        service.insertState(state);
    }
}
