package kr.co.rmtechs.waukids.scanner.scanner.service;

import kr.co.rmtechs.waukids.scanner.base.service.BaseService;
import kr.co.rmtechs.waukids.scanner.scanner.bean.ScannerVO;
import kr.co.rmtechs.waukids.scanner.scanner.mapper.ScannerMapper;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("scannerService")
public class ScannerServiceImpl implements BaseService<String, ScannerVO> {

    @Resource(name = "scannerMapper")
    private ScannerMapper mapper;

    @Override
    public List<ScannerVO> lists() {
        return mapper.selectScanners();
    }

    @Override
    public ScannerVO get(final String ipAddress) {
        return mapper.selectScanner(ipAddress);
    }

    @Override
    public void insert(final ScannerVO vo) {
    }
}
