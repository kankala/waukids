package kr.co.rmtechs.waukids.scanner.scanner.mapper;

import kr.co.rmtechs.waukids.scanner.scanner.bean.ScannerVO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository(value = "scannerMapper")
public interface ScannerMapper {

    List<ScannerVO> selectScanners();

    ScannerVO selectScanner(String ipAddress);

}
