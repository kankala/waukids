package kr.co.rmtechs.waukids.scanner.attendance.manager;

import org.springframework.stereotype.Service;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;

@Service("outAttendanceManager")
public class OutAttendanceImpl extends AbstractAttendance {

    @Override
    public void attend(final StudentVO student) {
        if (student == null) {
            return;
        }

        student.setStay(true);
        student.setAttendCount(0);
    }
}
