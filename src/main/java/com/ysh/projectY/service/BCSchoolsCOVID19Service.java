package com.ysh.projectY.service;

import com.ysh.projectY.dao.BCSchoolsCOVID19Dao;
import com.ysh.projectY.entity.BCSchoolsCOVID19;
import com.ysh.projectY.entity.HealthRegion;
import com.ysh.projectY.entity.SchoolsInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BCSchoolsCOVID19Service {

    final BCSchoolsCOVID19Dao bcSchoolsCOVID19Dao;

    public BCSchoolsCOVID19Service(BCSchoolsCOVID19Dao bcSchoolsCOVID19Dao) {
        this.bcSchoolsCOVID19Dao = bcSchoolsCOVID19Dao;
    }

    public void saveAndFlush(BCSchoolsCOVID19 bcSchoolsCOVID19) {
        bcSchoolsCOVID19Dao.saveAndFlush(bcSchoolsCOVID19);
    }

    public Optional<BCSchoolsCOVID19> findById(int id) {
        return bcSchoolsCOVID19Dao.findById(id);
    }

    public Set<BCSchoolsCOVID19> findByHealthRegion(HealthRegion healthRegion) {
        return bcSchoolsCOVID19Dao.findByHealthRegion(healthRegion);
    }

    public Set<BCSchoolsCOVID19> findBySchoolsInfo(SchoolsInfo schoolsInfo) {
        return bcSchoolsCOVID19Dao.findBySchoolsInfo(schoolsInfo);
    }

    public int countBySchoolsInfo(SchoolsInfo schoolsInfo) {
        return bcSchoolsCOVID19Dao.countBySchoolsInfo(schoolsInfo);
    }
}