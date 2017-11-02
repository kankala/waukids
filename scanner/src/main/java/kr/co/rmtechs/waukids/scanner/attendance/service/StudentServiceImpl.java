package kr.co.rmtechs.waukids.scanner.attendance.service;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;
import kr.co.rmtechs.waukids.scanner.attendance.mapper.StudentMapper;
import kr.co.rmtechs.waukids.scanner.state.bean.StateVO;
import kr.co.rmtechs.waukids.scanner.state.service.StateService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Resource(name = "studentMapper")
    private StudentMapper mapper;

    @Autowired
    private StateService stateService;

    @Override
    public List<StudentVO> lists() {
        return mapper.selectStudents();
    }

    @Override
    public void insert(final StudentVO vo) {
        final StudentVO student = mapper.selectAttend(vo);

        if (student != null) {
            student.setAttend(vo.getAttend());

            mapper.updateAttend(student);
        }
        else {
            mapper.insertAttend(vo);
        }
    }

    @Override
    public void insertState(final StateVO vo) {
        stateService.insert(vo);
    }

    @Override
    public StudentVO get(final Integer queryId) {
        return null;
    }

    @Override
    public void updateBattery(final StudentVO student) {
        mapper.updateBattery(student);
    }
}
