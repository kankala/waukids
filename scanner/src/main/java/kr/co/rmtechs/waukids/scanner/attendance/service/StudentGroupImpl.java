package kr.co.rmtechs.waukids.scanner.attendance.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rmtechs.waukids.scanner.attendance.bean.StudentVO;
import kr.co.rmtechs.waukids.scanner.base.service.BaseService;

@Repository("studentGroup")
public class StudentGroupImpl implements StudentGroup {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Resource(name = "studentService")
    private BaseService<Integer, StudentVO> studentService;

    private final ConcurrentMap<Integer, ConcurrentMap<String, StudentVO>> beaconMaps = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        logger.info("Initialize students for Beacon Information.");

        final List<StudentVO> students = studentService.lists();

        for (final StudentVO student : students) {
            final int schoolId = student.getSchoolId();

            ConcurrentMap<String, StudentVO> schoolMaps = beaconMaps.get(schoolId);

            if (schoolMaps == null) {
                final ConcurrentMap<String, StudentVO> studentMaps = new ConcurrentHashMap<>();

                beaconMaps.put(schoolId, studentMaps);
                schoolMaps = beaconMaps.get(schoolId);
            }

            schoolMaps.put(student.getBeaconId().replace(":", "").toLowerCase(), student);
        }

        logger.info("student : " + students.size());
    }

    public void reset() {
        logger.info("Reset students for Beacon Information.");

        beaconMaps.clear();

        init();
    }

    @Override
    public StudentVO get(final String beaconId) {
        for (final ConcurrentMap<String, StudentVO> studentMaps : beaconMaps.values()) {
            final StudentVO student = studentMaps.get(beaconId);
            if (student != null) {
                return student;
            }
        }

        return null;
    }

    @Override
    public Collection<StudentVO> getStudents(final int schoolId) {
        final ConcurrentMap<String, StudentVO> schoolMaps = beaconMaps.get(schoolId);

        if (schoolMaps != null) {
            return schoolMaps.values();
        }

        return Collections.emptyList();
    }

    @Override
    public List<StudentVO> lists() {
        return Collections.emptyList();
    }

    @Override
    public void insert(final StudentVO vo) {
    }

    @Override
    public Collection<StudentVO> getStudentsInStay(final int schoolId) {
        final ConcurrentMap<String, StudentVO> schoolMaps = beaconMaps.get(schoolId);

        if (schoolMaps == null) {
            return Collections.emptyList();
        }

        final List<StudentVO> students = new ArrayList<>();

        for (final StudentVO student : schoolMaps.values()) {
            if (student.getAttend() == StudentVO.STATE_ATTENDANCE) {
                students.add(student);
            }
        }

        return students;
    }
}
