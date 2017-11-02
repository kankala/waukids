package kr.co.rmtechs.waukids.scanner.state.service;

import kr.co.rmtechs.waukids.scanner.state.bean.StateVO;
import kr.co.rmtechs.waukids.scanner.state.mapper.StateMapper;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("stateService")
public class StateServiceImpl implements StateService {

    @Resource(name = "stateMapper")
    private StateMapper mapper;

    @Override
    public void insert(final StateVO vo) {
        mapper.insert(vo);
    }

    @Override
    public List<StateVO> lists() {
        return Collections.emptyList();
    }

    @Override
    public StateVO get(final Void queryId) {
        return null;
    }
}
