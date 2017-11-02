package kr.co.rmtechs.waukids.scanner.attendance.service;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;
import kr.co.rmtechs.waukids.scanner.base.service.BaseService;
import kr.co.rmtechs.waukids.scanner.state.bean.StateVO;

public interface StudentService extends BaseService<Integer, StudentVO> {
    void insertState(final StateVO vo);

    void updateBattery(final StudentVO student);
}
