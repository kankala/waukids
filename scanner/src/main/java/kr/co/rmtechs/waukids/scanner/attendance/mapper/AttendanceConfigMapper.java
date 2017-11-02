package kr.co.rmtechs.waukids.scanner.attendance.mapper;

import kr.co.rmtechs.waukids.scanner.attendance.bean.AttendanceConfigVO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository(value = "attendanceConfigMapper")
public interface AttendanceConfigMapper {

    AttendanceConfigVO selectConfig(int schoolId);

    List<AttendanceConfigVO> selectConfigs();

}
