package kr.co.rmtechs.waukids.scanner.attendance.service;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;
import kr.co.rmtechs.waukids.scanner.base.service.BaseService;

import java.util.Collection;

public interface StudentGroup extends BaseService<String, StudentVO> {
    Collection<StudentVO> getStudents(int schoolId);

    Collection<StudentVO> getStudentsInStay(int schoolId);
}
