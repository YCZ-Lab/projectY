package com.ysh.projectY.service;

import com.ysh.projectY.dao.BCSchoolsCOVID19Dao;
import com.ysh.projectY.entity.BCSchoolsCOVID19;
import com.ysh.projectY.form.*;
import com.ysh.projectY.utils.MethodResponse;
import com.ysh.projectY.utils.dateTools;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

//    public Set<BCSchoolsCOVID19> findByHealthRegion(HealthRegion healthRegion) {
//        return bcSchoolsCOVID19Dao.findByHealthRegion(healthRegion);
//    }

//    public Set<BCSchoolsCOVID19> findBySchoolsInfo(SchoolsInfo schoolsInfo) {
//        return bcSchoolsCOVID19Dao.findBySchoolsInfo(schoolsInfo);
//    }

//    public int countBySchoolsInfo(SchoolsInfo schoolsInfo) {
//        return bcSchoolsCOVID19Dao.countBySchoolsInfo(schoolsInfo);
//    }

    public Page<BCSchoolsCOVID19> getBCSchoolsCOVID19(String schoolName, String notificationDate, String districtAbb, String notificationMethod, String healthRegionName, String cityName, Pageable pageable) {
        final Page<BCSchoolsCOVID19> page = bcSchoolsCOVID19Dao.findAllBySchoolNameContainingAndNotificationDateContainingAndDistrictAbbContainingAndNotificationMethodContainingAndHealthRegionNameContainingAndCityNameContaining(schoolName, notificationDate, districtAbb, notificationMethod, healthRegionName, cityName, pageable);
        return page;
    }

    public Set<BCSchoolsDistrictsCOVID19Summary> getBCSchoolsDistrictsCOVID19Summary(String startDate, String endDate) {
        return bcSchoolsCOVID19Dao.countByDistrictAbb(startDate, endDate);
    }

    public Set<BCSchoolsCOVID19Summary> getBCSchoolsCOVID19Summary(String startDate, String endDate) {
        return bcSchoolsCOVID19Dao.countBySchoolName(startDate, endDate);
    }

    public Set<BCSchoolsHealthsCOVID19Summary> getBCSchoolsHealthsCOVID19Summary(String startDate, String endDate) {
        return bcSchoolsCOVID19Dao.countByHealthName(startDate, endDate);
    }

    public Optional<BCSchoolsCOVID19TotalSummary> getBCSchoolsCOVID19TotalSummary() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BCSchoolsCOVID19TotalSummary bcSchoolsCOVID19TotalSummary = new BCSchoolsCOVID19TotalSummary();
        final long total = bcSchoolsCOVID19Dao.getSum();
        bcSchoolsCOVID19TotalSummary.setTotal(total);
        String updateDate = "2021-03-28";
        bcSchoolsCOVID19TotalSummary.setUpdateDateTime(updateDate);
        Date temp = null;
        try {
            temp = sdf.parse("2021-03-24");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateTime = dateTools.getThisWeekMonday(temp);
        String startDate = sdf.format(dateTools.getThisWeekMonday(dateTime));
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.add(Calendar.DATE, 6);
        String endDate = sdf.format(cal.getTime());
        System.out.println(startDate + " / " + endDate);
        final float thisWeek = bcSchoolsCOVID19Dao.countByNotificationDate(startDate, endDate);
        cal.add(Calendar.DATE, -7);
        endDate = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        startDate = sdf.format(cal.getTime());
        System.out.println(startDate + " / " + endDate);
        final float lastWeek = bcSchoolsCOVID19Dao.countByNotificationDate(startDate, endDate);
        bcSchoolsCOVID19TotalSummary.setWeeklyChanges(String.valueOf(Math.round(((thisWeek - lastWeek) / lastWeek) * 100)).replace(".0", ""));
        cal.setTime(temp);
        startDate = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        endDate = sdf.format(cal.getTime());
        System.out.println(startDate + "/ " + endDate);
        final float today = bcSchoolsCOVID19Dao.countByNotificationDate(startDate, startDate);
        final float yesterday = bcSchoolsCOVID19Dao.countByNotificationDate(endDate, endDate);
        bcSchoolsCOVID19TotalSummary.setDailyChanges(String.valueOf(Math.round(((today - yesterday) / yesterday) * 100)).replace(".0", ""));
        return Optional.of(bcSchoolsCOVID19TotalSummary);
    }

    public Set<BCSchoolsCOVID19DailySummary> getBCSchoolsCOVID19DailySummary(String startDate, String endDate) {
        Set<BCSchoolsCOVID19DailySummary> bcSchoolsCOVID19DailySummaries = bcSchoolsCOVID19Dao.countByNotificationDateGroupByNotificationDate(startDate, endDate);
        bcSchoolsCOVID19DailySummaries.addAll(bcSchoolsCOVID19Dao.countByHealthRegionNameNotificationDateGroupByNotificationDate(startDate, endDate));
        return bcSchoolsCOVID19DailySummaries;
    }

    public Set<BCSchoolsHealthsCitiesCOVID19Summary> getBCSchoolsHealthsCitiesCOVID19Summary(String startDate, String endDate) {
        return bcSchoolsCOVID19Dao.countByHealthsCities(startDate, endDate);
    }

    public Set<BCSchoolsCOVID19MonthlySummary> getBCSchoolsCOVID19MonthlySummary(String startDate, String endDate) {
        return bcSchoolsCOVID19Dao.countByNotificationDateMonthly(startDate, endDate);
    }

    public Set<BCSchoolCOVID19DailySummary> getBCSchoolCOVID19DailySummary(String startDate, String endDate, int schoolId) {
        return bcSchoolsCOVID19Dao.countByNotificationDateGroupBySchoolId(startDate, endDate, schoolId);
    }

    public MethodResponse getBCSchoolsCOVID19GoogleMapSummary() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -14);
            Set<BCSchoolsCOVID19GoogleMapSummary> bcSchoolsCOVID19GoogleMapSummary = bcSchoolsCOVID19Dao.getBCSchoolsCOVID19GoogleMapSummary(sdf.format(cal.getTime()));
            return MethodResponse.success("projectY.BCSchoolsCOVID19Service.getBCSchoolsCOVID19GoogleMapSummary.success", "", bcSchoolsCOVID19GoogleMapSummary);
        } catch (Exception e) {
            e.printStackTrace();
            return MethodResponse.failure("projectY.BCSchoolsCOVID19Service.getBCSchoolsCOVID19GoogleMapSummary.failure.Exception", e.toString(), null);
        }
    }
}
