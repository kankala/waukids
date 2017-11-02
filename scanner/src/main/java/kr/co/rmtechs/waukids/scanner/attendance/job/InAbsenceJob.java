package kr.co.rmtechs.waukids.scanner.attendance.job;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InAbsenceJob extends AbstractAbsenceJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void absence() {
        final Collection<StudentVO> students = groups.getStudents(schoolId);

        for (final StudentVO student : students) {
            if (student.getAttend() == StudentVO.STATE_NONE) {
                int attendCount = student.getAttendCount();

                if (attendCount == repeat) {
                    student.setAttend(StudentVO.STATE_ABSENCE);
                    student.setTime(endTime);

                    service.insert(student);

                    logger.info("결석 : " + student);
                }

                student.setAttendCount(++attendCount);
            }
        }
    }
}
