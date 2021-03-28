package com.ysh.projectY.dao;

import com.ysh.projectY.entity.BCSchoolsCOVID19;
import com.ysh.projectY.form.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface BCSchoolsCOVID19Dao extends JpaRepository<BCSchoolsCOVID19, Integer> {

//    public Set<BCSchoolsCOVID19> findByHealthRegion(HealthRegion healthRegion);

//    public Set<BCSchoolsCOVID19> findBySchoolsInfo(SchoolsInfo schoolsInfo);
//
//    public int countBySchoolsInfo(SchoolsInfo schoolsInfo);

    public Page<BCSchoolsCOVID19> findAllBySchoolNameContainingAndNotificationDateContainingAndDistrictAbbContainingAndNotificationMethodContainingAndHealthRegionNameContainingAndCityNameContaining(String schoolName, String notificationDate, String districtAbb, String notificationMethod, String healthRegionName, String cityName, Pageable pageable);

    @Query(value = "select district_abb as districtAbb, district_name as districtName, sum(exposure_number) as count from bc_school_covid19 where notification_date >=:startDate and notification_date<=:endDate group by district_abb, district_name order by district_abb", nativeQuery = true)
    Set<BCSchoolsDistrictsCOVID19Summary> countByDistrictAbb(String startDate, String endDate);

    @Query(value = "select school_name as schoolName, sum(exposure_number) as count from bc_school_covid19 where notification_date >=:startDate and notification_date<=:endDate group by school_name order by count desc", nativeQuery = true)
    Set<BCSchoolsCOVID19Summary> countBySchoolName(String startDate, String endDate);

    @Query(value = "select health_region_name as healthRegionName, sum(exposure_number) as count from bc_school_covid19 where notification_date >=:startDate and notification_date<=:endDate group by health_region_name order by health_region_name", nativeQuery = true)
    Set<BCSchoolsHealthsCOVID19Summary> countByHealthName(String startDate, String endDate);

    @Query(value = "select sum(exposure_number) from bc_school_covid19 where notification_date >=:startDate and notification_date<=:endDate", nativeQuery = true)
    int countByNotificationDate(String startDate, String endDate);

    @Query(value = "select 'All' as 'healthRegionName', notification_date as day,sum(exposure_number) as count from bc_school_covid19 where notification_date >=:startDate and notification_date<=:endDate group by notification_date order by notification_date", nativeQuery = true)
    Set<BCSchoolsCOVID19DailySummary> countByNotificationDateGroupByNotificationDate(String startDate, String endDate);

    @Query(value = "select health_region_name as healthRegionName, notification_date as day,sum(exposure_number) as count from bc_school_covid19 where notification_date >=:startDate and notification_date<=:endDate group by health_region_name,notification_date order by health_region_name,notification_date", nativeQuery = true)
    Set<BCSchoolsCOVID19DailySummary> countByHealthRegionNameNotificationDateGroupByNotificationDate(String startDate, String endDate);

    @Query(value = "select health_region_name as healthRegionName, city_name as cityName,sum(exposure_number) as count from bc_school_covid19 where notification_date >=:startDate and notification_date<=:endDate group by health_region_name,city_name order by health_region_name,city_name", nativeQuery = true)
    Set<BCSchoolsHealthsCitiesCOVID19Summary> countByHealthsCities(String startDate, String endDate);

    @Query(value = "select substr(notification_date, 1, 7) as month, sum(exposure_number) as count from bc_school_covid19 where notification_date >=:startDate and notification_date<=:endDate group by month order by month", nativeQuery = true)
    Set<BCSchoolsCOVID19MonthlySummary> countByNotificationDateMonthly(String startDate, String endDate);

    @Query(value = "select notification_date as day,sum(exposure_number) as count from bc_school_covid19 where notification_date >=:startDate and notification_date<=:endDate and school_id=:schoolId group by notification_date order by notification_date", nativeQuery = true)
    Set<BCSchoolCOVID19DailySummary> countByNotificationDateGroupBySchoolId(String startDate, String endDate, int schoolId);

    @Query(value = "select b.school_code as schoolCode,b.school_name as schoolName,b.latitude as latitude,b.longitude as longitude,b.grade_range as gradeRange,b.enrolment_total as enrolmentTotal,b.school_website_google_map as website,a.count as count from (select schools_info_id ,sum(exposure_number) as count from bc_school_covid19 where notification_date>=:startDate group by schools_info_id) a left join schools_info b on a.schools_info_id=b.id order by b.id", nativeQuery = true)
    Set<BCSchoolsCOVID19GoogleMapSummary> getBCSchoolsCOVID19GoogleMapSummary(String startDate);

    @Query(value = "select sum(exposure_number) from bc_school_covid19", nativeQuery = true)
    long getSum();

}
