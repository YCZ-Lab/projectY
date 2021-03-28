package com.ysh.projectY.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "bc_school_covid19")
public class BCSchoolsCOVID19 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", length = 32, nullable = false)
    private int id;

    // 双向关联
    @ManyToOne(fetch = FetchType.EAGER)
    private SchoolsInfo schoolsInfo;

    @Column(name = "`school_id`", columnDefinition = "int NOT NULL comment '学校ID'")
    private int schoolId;

    @Column(name = "`school_name`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '学校名称'")
    private String schoolName;

    @Column(name = "`district_id`", columnDefinition = "int NOT NULL comment '教育局ID'")
    private int districtId;

    @Column(name = "`district_name`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '教育局名称'")
    private String districtName;

    @Column(name = "`district_abb`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '教育局简称'")
    private String districtAbb;

    @Column(name = "`city_id`", columnDefinition = "int NOT NULL comment '城市ID'")
    private int cityId;

    @Column(name = "`city_name`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '城市名称'")
    private String cityName;

    // 双向关联
    @ManyToOne(fetch = FetchType.EAGER)
    private HealthRegion healthRegion;

    @Column(name = "`health_id`", columnDefinition = "int NOT NULL comment '卫生局ID'")
    private int healthId;

    @Column(name = "`health_region_name`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '卫生局名称'")
    private String healthRegionName;

    @Column(name = "`notification_date`", columnDefinition = "varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '通知日期'")
//    @Temporal(TemporalType.DATE)
    private String notificationDate;

    @Column(name = "`notification_method`", columnDefinition = "varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '通知方式'")
    private String notificationMethod;

    @Column(name = "`exposure_date`", columnDefinition = "varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '感染日期'")
    private String exposureDate;

    @Column(name = "`exposure_number`", columnDefinition = "int NOT NULL comment '感染人数'")
    private int exposureNumber;

    @Column(name = "`extra_info`", columnDefinition = "varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '额外信息'")
    private String extraInfo;

    @Column(name = "`documentation`", columnDefinition = "varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '文档'")
    private String documentation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SchoolsInfo getSchoolsInfo() {
        return schoolsInfo;
    }

    public void setSchoolsInfo(SchoolsInfo schoolsInfo) {
        this.schoolsInfo = schoolsInfo;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictAbb() {
        return districtAbb;
    }

    public void setDistrictAbb(String districtAbb) {
        this.districtAbb = districtAbb;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public HealthRegion getHealthRegion() {
        return healthRegion;
    }

    public void setHealthRegion(HealthRegion healthRegion) {
        this.healthRegion = healthRegion;
    }


    public String getHealthRegionName() {
        return healthRegionName;
    }

    public int getHealthId() {
        return healthId;
    }

    public void setHealthId(int healthId) {
        this.healthId = healthId;
    }

    public void setHealthRegionName(String healthRegionName) {
        this.healthRegionName = healthRegionName;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationMethod() {
        return notificationMethod;
    }

    public void setNotificationMethod(String notificationMethod) {
        this.notificationMethod = notificationMethod;
    }

    public String getExposureDate() {
        return exposureDate;
    }

    public void setExposureDate(String exposureDate) {
        this.exposureDate = exposureDate;
    }

    public int getExposureNumber() {
        return exposureNumber;
    }

    public void setExposureNumber(int exposureNumber) {
        this.exposureNumber = exposureNumber;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

//    @Override
//    public String toString() {
//        return "BCSchoolsCOVID19{\n" +
//                "id = " + id +
////                ", \nschoolsInfo = " + schoolsInfo +
//                ", \nschoolId = " + schoolId +
//                ", \nschoolName = '" + schoolName + '\'' +
//                ", \ndistrictId = " + districtId +
//                ", \ndistrictName = '" + districtName + '\'' +
//                ", \ndistrictAbb = '" + districtAbb + '\'' +
//                ", \ncityId = " + cityId +
//                ", \ncityName = '" + cityName + '\'' +
////                ", \nhealthRegion = " + healthRegion +
//                ", \nhealthId = " + healthId +
//                ", \nhealthRegionName = '" + healthRegionName + '\'' +
//                ", \nnotificationDate = " + notificationDate +
//                ", \nnotificationMethod = '" + notificationMethod + '\'' +
//                ", \nexposureDate = '" + exposureDate + '\'' +
//                ", \nexposureNumber = " + exposureNumber +
//                ", \nextraInfo = '" + extraInfo + '\'' +
//                ", \ndocumentation = '" + documentation + '\'' +
//                "\n}";
//    }
}
