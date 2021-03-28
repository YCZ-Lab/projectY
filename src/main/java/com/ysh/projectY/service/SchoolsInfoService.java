package com.ysh.projectY.service;

import com.ysh.projectY.dao.SchoolsInfoDao;
import com.ysh.projectY.entity.SchoolsInfo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolsInfoService {

    final SchoolsInfoDao schoolsInfoDao;

    public SchoolsInfoService(SchoolsInfoDao schoolsInfoDao) {
        this.schoolsInfoDao = schoolsInfoDao;
    }

    public Optional<SchoolsInfo> findBySchoolName(String schoolName) {
        return schoolsInfoDao.findBySchoolName(schoolName);
    }

    public List<SchoolsInfo> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return schoolsInfoDao.findAll(sort);
    }

    public Optional<SchoolsInfo> findById(int id) {
        return schoolsInfoDao.findById(id);
    }

    public void saveAndFlush(SchoolsInfo schoolsInfo) {
        schoolsInfoDao.saveAndFlush(schoolsInfo);
    }

    public List<SchoolsInfo> findAllByPostalcode() {
        return schoolsInfoDao.findAllByPostalcode();
    }

    public Optional<SchoolsInfo> findByExternalImportNameContaining(String schoolName) {
        return schoolsInfoDao.findByExternalImportNameContaining(schoolName);
    }

    public int countByExternalImportNameContaining(String schoolName) {
        return schoolsInfoDao.countByExternalImportNameContaining(schoolName);
    }
}
