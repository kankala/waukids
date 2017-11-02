package kr.co.rmtechs.waukids.scanner.state.mapper;

import kr.co.rmtechs.waukids.scanner.state.bean.StateVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository(value = "stateMapper")
public interface StateMapper {

    void insert(StateVO vo);
}
