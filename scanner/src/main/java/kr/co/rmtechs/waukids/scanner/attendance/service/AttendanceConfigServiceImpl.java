package kr.co.rmtechs.waukids.scanner.attendance.service;

import kr.co.rmtechs.waukids.scanner.attendance.bean.AttendanceConfigVO;
import kr.co.rmtechs.waukids.scanner.attendance.mapper.AttendanceConfigMapper;
import kr.co.rmtechs.waukids.scanner.base.service.BaseService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("attendanceConfigService")
public class AttendanceConfigServiceImpl implements BaseService<Integer, AttendanceConfigVO> {

    @Resource(name = "attendanceConfigMapper")
    private AttendanceConfigMapper mapper;

    @Override
    public AttendanceConfigVO get(final Integer schoolId) {
        return mapper.selectConfig(schoolId);
    }

    @Override
    public List<AttendanceConfigVO> lists() {
        return mapper.selectConfigs();
    }

    @Override
    public void insert(final AttendanceConfigVO vo) {
    }
}
