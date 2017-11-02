package kr.co.rmtechs.waukids.scanner.attendance.mapper;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository(value = "studentMapper")
public interface StudentMapper {

    List<StudentVO> selectStudents();

    StudentVO selectAttend(StudentVO vo);

    void insertAttend(StudentVO vo);

    void updateAttend(StudentVO vo);

    void updateBattery(StudentVO vo);
}
