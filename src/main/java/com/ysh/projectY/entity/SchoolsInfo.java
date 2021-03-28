package com.ysh.projectY.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "schools_info")
public class SchoolsInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", length = 32, nullable = false)
    private int id;

    @Column(name = "`school_code`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '学校编码'")
    private String schoolCode;

    @Column(name = "`school_name`", columnDefinition = "varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '学校名称'")
    private String schoolName;

    @Column(name = "`school_name_google_name`", columnDefinition = "varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '学校名称(Google Map)'")
    private String schoolNameGoogleMap;

    @Column(name = "`external_import_name`", columnDefinition = "varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '匹配外部导入数据的学校名称'")
    private String externalImportName;

    @ColumnDefault("1")
    @Column(name = "`enabled`", columnDefinition = "tinyint(1) NOT NULL comment '启用/禁用'")
    private boolean enabled;

    // 双向关联
//    @OneToMany(mappedBy = "schoolsInfo", fetch = FetchType.LAZY)
//    Set<BCSchoolsCOVID19> bcSchoolsCOVID19;

    // 双向关联
    @ManyToOne(fetch = FetchType.EAGER)
    SchoolDistrict schoolDistrict;

    @Column(name = "`no_district_number`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '教育局编号'")
    private String noDistrictNumber;

    @Column(name = "`school_address`", columnDefinition = "varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '学校地址'")
    private String schoolAddress;

    @Column(name = "`postal_code`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '邮编'")
    private String postalCode;

    @Column(name = "`longitude`", columnDefinition = "decimal(10,6) NOT NULL comment '经度'")
    private Double longitude;

    @Column(name = "`latitude`", columnDefinition = "decimal(10,6) NOT NULL comment '纬度'")
    private Double latitude;

    @Column(name = "`school_address_google_map`", columnDefinition = "varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '学校地址(Google Map)'")
    private String schoolAddressGoogleMap;

    @Column(name = "`postal_code_google_map`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '邮编(Google Map)'")
    private String postalCodeGoogleMap;

    @Column(name = "`place_id_google_map`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '位置ID(Google Map)'")
    private String placeIdGoogleMap;

    // 双向关联
    @ManyToOne(fetch = FetchType.EAGER)
    CitiesInfo citiesInfo;

    @Column(name = "`no_city`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '城市'")
    private String noCity;

    @Column(name = "`no_province`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '省份'")
    private String noProvince;

    @Column(name = "`principal_title`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '先生/女士'")
    private String principalTitle;

    @Column(name = "`principal_first_name`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '校长名字'")
    private String principalFirstName;

    @Column(name = "`principal_last_name`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '姓'")
    private String principalLastName;

    @Column(name = "`school_type`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '学校类型'")
    private String schoolType;

    @Column(name = "`grade_range`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '年级范围'")
    private String gradeRange;

    @Column(name = "`school_category`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '学校类别(公校/私校)'")
    private String schoolCategory;

    @Column(name = "`funding_groups`", columnDefinition = "int NOT NULL comment '资助团体'")
    private int fundingGroups;

    @Column(name = "`nlc_early_learning`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '???'")
    private String nlcEarlyLearning;

    @Column(name = "`nlc_after_school`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '???'")
    private String nlcAfterSchool;

    @Column(name = "`nlc_cont_ed`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '???'")
    private String nlcContEd;

    @Column(name = "`nlc_seniors`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '???'")
    private String nlcSeniors;

    @Column(name = "`nlc_comm_sport`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '???'")
    private String nlcCommSport;

    @Column(name = "`nlc_comm_use`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '???'")
    private String nlcCommUse;

    @Column(name = "`nlc_integr_svcs`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '???'")
    private String nlcIntegrSvcs;

    @Column(name = "`school_phone`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '电话'")
    private String schoolPhone;

    @Column(name = "`school_fax`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '传真'")
    private String schoolFax;

    @Column(name = "`school_email`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '电子邮件'")
    private String schoolEmail;

    @Column(name = "`school_phone_google_map`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '电话(Google Map)'")
    private String schoolPhoneGoogleMap;

    @Column(name = "`google_map_url_google_map`", columnDefinition = "varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment 'URL(Google Map)'")
    private String googleMapUrlGoogleMap;

    @Column(name = "`school_website_google_map`", columnDefinition = "varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '学校网站(Google Map)'")
    private String schoolWebsiteGoogleMap;

    @Column(name = "`enrolment_total`", columnDefinition = "int NOT NULL comment '招生总数'")
    private int enrolmentTotal;

    @Column(name = "`enrolment_as_of`", columnDefinition = "varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '截止报名'")
    private String enrolmentAsOf;

    @Column(name = "`kh_enrolment`", columnDefinition = "int NOT NULL comment '???报名'")
    private int khEnrolment;

    @Column(name = "`kf_enrolment`", columnDefinition = "int NOT NULL comment '???报名'")
    private int kfEnrolment;

    @Column(name = "`hs_registration`", columnDefinition = "int NOT NULL comment '???注册'")
    private int hsRegistration;

    @Column(name = "`su_enrolment`", columnDefinition = "int NOT NULL comment '???报名'")
    private int suEnrolment;

    @Column(name = "`eu_enrolment`", columnDefinition = "int NOT NULL comment '???报名'")
    private int euEnrolment;

    @Column(name = "`grade_1_enrolment`", columnDefinition = "int NOT NULL comment '1年级在校人数'")
    private int grade1Enrolment;

    @Column(name = "`grade_2_enrolment`", columnDefinition = "int NOT NULL comment '2年级在校人数'")
    private int grade2Enrolment;

    @Column(name = "`grade_3_enrolment`", columnDefinition = "int NOT NULL comment '3年级在校人数'")
    private int grade3Enrolment;

    @Column(name = "`grade_4_enrolment`", columnDefinition = "int NOT NULL comment '4年级在校人数'")
    private int grade4Enrolment;

    @Column(name = "`grade_5_enrolment`", columnDefinition = "int NOT NULL comment '5年级在校人数'")
    private int grade5Enrolment;

    @Column(name = "`grade_6_enrolment`", columnDefinition = "int NOT NULL comment '6年级在校人数'")
    private int grade6Enrolment;

    @Column(name = "`grade_7_enrolment`", columnDefinition = "int NOT NULL comment '7年级在校人数'")
    private int grade7Enrolment;

    @Column(name = "`grade_8_enrolment`", columnDefinition = "int NOT NULL comment '8年级在校人数'")
    private int grade8Enrolment;

    @Column(name = "`grade_9_enrolment`", columnDefinition = "int NOT NULL comment '9年级在校人数'")
    private int grade9Enrolment;

    @Column(name = "`grade_10_enrolment`", columnDefinition = "int NOT NULL comment '10年级在校人数'")
    private int grade10Enrolment;

    @Column(name = "`grade_11_enrolment`", columnDefinition = "int NOT NULL comment '11年级在校人数'")
    private int grade11Enrolment;

    @Column(name = "`grade_12_enrolment`", columnDefinition = "int NOT NULL comment '12年级在校人数'")
    private int grade12Enrolment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolNameGoogleMap() {
        return schoolNameGoogleMap;
    }

    public void setSchoolNameGoogleMap(String schoolNameGoogleMap) {
        this.schoolNameGoogleMap = schoolNameGoogleMap;
    }

    public String getExternalImportName() {
        return externalImportName;
    }

    public void setExternalImportName(String externalImportName) {
        this.externalImportName = externalImportName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

//    public Set<BCSchoolsCOVID19> getBcSchoolsCOVID19() {
//        return bcSchoolsCOVID19;
//    }
//
//    public void setBcSchoolsCOVID19(Set<BCSchoolsCOVID19> bcSchoolsCOVID19) {
//        this.bcSchoolsCOVID19 = bcSchoolsCOVID19;
//    }

    public SchoolDistrict getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(SchoolDistrict schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }

    public String getDistrictNumber() {
        return noDistrictNumber;
    }

    public void setDistrictNumber(String districtNumber) {
        this.noDistrictNumber = districtNumber;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getSchoolAddressGoogleMap() {
        return schoolAddressGoogleMap;
    }

    public void setSchoolAddressGoogleMap(String schoolAddressGoogleMap) {
        this.schoolAddressGoogleMap = schoolAddressGoogleMap;
    }

    public String getPostalCodeGoogleMap() {
        return postalCodeGoogleMap;
    }

    public void setPostalCodeGoogleMap(String postalCodeGoogleMap) {
        this.postalCodeGoogleMap = postalCodeGoogleMap;
    }

    public String getPlaceIdGoogleMap() {
        return placeIdGoogleMap;
    }

    public void setPlaceIdGoogleMap(String placeIdGoogleMap) {
        this.placeIdGoogleMap = placeIdGoogleMap;
    }

    public CitiesInfo getCitiesInfo() {
        return citiesInfo;
    }

    public void setCitiesInfo(CitiesInfo citiesInfo) {
        this.citiesInfo = citiesInfo;
    }

    public String getCity() {
        return noCity;
    }

    public void setCity(String city) {
        this.noCity = city;
    }

    public String getProvince() {
        return noProvince;
    }

    public void setProvince(String province) {
        this.noProvince = province;
    }

    public String getPrincipalTitle() {
        return principalTitle;
    }

    public void setPrincipalTitle(String principalTitle) {
        this.principalTitle = principalTitle;
    }

    public String getPrincipalFirstName() {
        return principalFirstName;
    }

    public void setPrincipalFirstName(String principalFirstName) {
        this.principalFirstName = principalFirstName;
    }

    public String getPrincipalLastName() {
        return principalLastName;
    }

    public void setPrincipalLastName(String principalLastName) {
        this.principalLastName = principalLastName;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getGradeRange() {
        return gradeRange;
    }

    public void setGradeRange(String gradeRange) {
        this.gradeRange = gradeRange;
    }

    public String getSchoolCategory() {
        return schoolCategory;
    }

    public void setSchoolCategory(String schoolCategory) {
        this.schoolCategory = schoolCategory;
    }

    public int getFundingGroups() {
        return fundingGroups;
    }

    public void setFundingGroups(int fundingGroups) {
        this.fundingGroups = fundingGroups;
    }

    public String getNlcEarlyLearning() {
        return nlcEarlyLearning;
    }

    public void setNlcEarlyLearning(String nlcEarlyLearning) {
        this.nlcEarlyLearning = nlcEarlyLearning;
    }

    public String getNlcAfterSchool() {
        return nlcAfterSchool;
    }

    public void setNlcAfterSchool(String nlcAfterSchool) {
        this.nlcAfterSchool = nlcAfterSchool;
    }

    public String getNlcContEd() {
        return nlcContEd;
    }

    public void setNlcContEd(String nlcContEd) {
        this.nlcContEd = nlcContEd;
    }

    public String getNlcSeniors() {
        return nlcSeniors;
    }

    public void setNlcSeniors(String nlcSeniors) {
        this.nlcSeniors = nlcSeniors;
    }

    public String getNlcCommSport() {
        return nlcCommSport;
    }

    public void setNlcCommSport(String nlcCommSport) {
        this.nlcCommSport = nlcCommSport;
    }

    public String getNlcCommUse() {
        return nlcCommUse;
    }

    public void setNlcCommUse(String nlcCommUse) {
        this.nlcCommUse = nlcCommUse;
    }

    public String getNlcIntegrSvcs() {
        return nlcIntegrSvcs;
    }

    public void setNlcIntegrSvcs(String nlcIntegrSvcs) {
        this.nlcIntegrSvcs = nlcIntegrSvcs;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public String getSchoolFax() {
        return schoolFax;
    }

    public void setSchoolFax(String schoolFax) {
        this.schoolFax = schoolFax;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getSchoolPhoneGoogleMap() {
        return schoolPhoneGoogleMap;
    }

    public void setSchoolPhoneGoogleMap(String schoolPhoneGoogleMap) {
        this.schoolPhoneGoogleMap = schoolPhoneGoogleMap;
    }

    public String getGoogleMapUrlGoogleMap() {
        return googleMapUrlGoogleMap;
    }

    public void setGoogleMapUrlGoogleMap(String googleMapUrlGoogleMap) {
        this.googleMapUrlGoogleMap = googleMapUrlGoogleMap;
    }

    public String getSchoolWebsiteGoogleMap() {
        return schoolWebsiteGoogleMap;
    }

    public void setSchoolWebsiteGoogleMap(String schoolWebsiteGoogleMap) {
        this.schoolWebsiteGoogleMap = schoolWebsiteGoogleMap;
    }

    public int getEnrolmentTotal() {
        return enrolmentTotal;
    }

    public void setEnrolmentTotal(int enrolmentTotal) {
        this.enrolmentTotal = enrolmentTotal;
    }

    public String getEnrolmentAsOf() {
        return enrolmentAsOf;
    }

    public void setEnrolmentAsOf(String enrolmentAsOf) {
        this.enrolmentAsOf = enrolmentAsOf;
    }

    public int getKhEnrolment() {
        return khEnrolment;
    }

    public void setKhEnrolment(int khEnrolment) {
        this.khEnrolment = khEnrolment;
    }

    public int getKfEnrolment() {
        return kfEnrolment;
    }

    public void setKfEnrolment(int kfEnrolment) {
        this.kfEnrolment = kfEnrolment;
    }

    public int getHsRegistration() {
        return hsRegistration;
    }

    public void setHsRegistration(int hsRegistration) {
        this.hsRegistration = hsRegistration;
    }

    public int getSuEnrolment() {
        return suEnrolment;
    }

    public void setSuEnrolment(int suEnrolment) {
        this.suEnrolment = suEnrolment;
    }

    public int getEuEnrolment() {
        return euEnrolment;
    }

    public void setEuEnrolment(int euEnrolment) {
        this.euEnrolment = euEnrolment;
    }

    public int getGrade1Enrolment() {
        return grade1Enrolment;
    }

    public void setGrade1Enrolment(int grade1Enrolment) {
        this.grade1Enrolment = grade1Enrolment;
    }

    public int getGrade2Enrolment() {
        return grade2Enrolment;
    }

    public void setGrade2Enrolment(int grade2Enrolment) {
        this.grade2Enrolment = grade2Enrolment;
    }

    public int getGrade3Enrolment() {
        return grade3Enrolment;
    }

    public void setGrade3Enrolment(int grade3Enrolment) {
        this.grade3Enrolment = grade3Enrolment;
    }

    public int getGrade4Enrolment() {
        return grade4Enrolment;
    }

    public void setGrade4Enrolment(int grade4Enrolment) {
        this.grade4Enrolment = grade4Enrolment;
    }

    public int getGrade5Enrolment() {
        return grade5Enrolment;
    }

    public void setGrade5Enrolment(int grade5Enrolment) {
        this.grade5Enrolment = grade5Enrolment;
    }

    public int getGrade6Enrolment() {
        return grade6Enrolment;
    }

    public void setGrade6Enrolment(int grade6Enrolment) {
        this.grade6Enrolment = grade6Enrolment;
    }

    public int getGrade7Enrolment() {
        return grade7Enrolment;
    }

    public void setGrade7Enrolment(int grade7Enrolment) {
        this.grade7Enrolment = grade7Enrolment;
    }

    public int getGrade8Enrolment() {
        return grade8Enrolment;
    }

    public void setGrade8Enrolment(int grade8Enrolment) {
        this.grade8Enrolment = grade8Enrolment;
    }

    public int getGrade9Enrolment() {
        return grade9Enrolment;
    }

    public void setGrade9Enrolment(int grade9Enrolment) {
        this.grade9Enrolment = grade9Enrolment;
    }

    public int getGrade10Enrolment() {
        return grade10Enrolment;
    }

    public void setGrade10Enrolment(int grade10Enrolment) {
        this.grade10Enrolment = grade10Enrolment;
    }

    public int getGrade11Enrolment() {
        return grade11Enrolment;
    }

    public void setGrade11Enrolment(int grade11Enrolment) {
        this.grade11Enrolment = grade11Enrolment;
    }

    public int getGrade12Enrolment() {
        return grade12Enrolment;
    }

    public void setGrade12Enrolment(int grade12Enrolment) {
        this.grade12Enrolment = grade12Enrolment;
    }

//    @Override
//    public String toString() {
//        return "SchoolsInfo{\n" +
//                "id = " + id +
//                ", \nschoolCode = '" + schoolCode + '\'' +
//                ", \nschoolName = '" + schoolName + '\'' +
//                ", \nenabled = " + enabled +
////                ", \nbcSchoolsCOVID19 = " + bcSchoolsCOVID19.getClass().getName() +
//                ", \nschoolDistrict = " + schoolDistrict +
//                ", \nnoDistrictNumber = '" + noDistrictNumber + '\'' +
//                ", \nschoolAddress = '" + schoolAddress + '\'' +
//                ", \npostalCode = '" + postalCode + '\'' +
//                ", \nlongitude = " + longitude +
//                ", \nlatitude = " + latitude +
//                ", \ncitiesInfo = " + citiesInfo +
//                ", \nnoCity = '" + noCity + '\'' +
//                ", \nnoProvince = '" + noProvince + '\'' +
//                ", \nprincipalTitle = '" + principalTitle + '\'' +
//                ", \nprincipalFirstName = '" + principalFirstName + '\'' +
//                ", \nprincipalLastName = '" + principalLastName + '\'' +
//                ", \nschoolType = '" + schoolType + '\'' +
//                ", \ngradeRange = '" + gradeRange + '\'' +
//                ", \nschoolCategory = '" + schoolCategory + '\'' +
//                ", \nfundingGroups = " + fundingGroups +
//                ", \nnlcEarlyLearning = '" + nlcEarlyLearning + '\'' +
//                ", \nnlcAfterSchool = '" + nlcAfterSchool + '\'' +
//                ", \nnlcContEd = '" + nlcContEd + '\'' +
//                ", \nnlcSeniors = '" + nlcSeniors + '\'' +
//                ", \nnlcCommSport = '" + nlcCommSport + '\'' +
//                ", \nnlcCommUse = '" + nlcCommUse + '\'' +
//                ", \nnlcIntegrSvcs = '" + nlcIntegrSvcs + '\'' +
//                ", \nschoolPhone = '" + schoolPhone + '\'' +
//                ", \nschoolFax = '" + schoolFax + '\'' +
//                ", \nschoolEmail = '" + schoolEmail + '\'' +
//                ", \nenrolmentTotal = " + enrolmentTotal +
//                ", \nenrolmentAsOf = '" + enrolmentAsOf + '\'' +
//                ", \nkhEnrolment = " + khEnrolment +
//                ", \nkfEnrolment = " + kfEnrolment +
//                ", \nhsRegistration = " + hsRegistration +
//                ", \nsuEnrolment = " + suEnrolment +
//                ", \neuEnrolment = " + euEnrolment +
//                ", \ngrade1Enrolment = " + grade1Enrolment +
//                ", \ngrade2Enrolment = " + grade2Enrolment +
//                ", \ngrade3Enrolment = " + grade3Enrolment +
//                ", \ngrade4Enrolment = " + grade4Enrolment +
//                ", \ngrade5Enrolment = " + grade5Enrolment +
//                ", \ngrade6Enrolment = " + grade6Enrolment +
//                ", \ngrade7Enrolment = " + grade7Enrolment +
//                ", \ngrade8Enrolment = " + grade8Enrolment +
//                ", \ngrade9Enrolment = " + grade9Enrolment +
//                ", \ngrade10Enrolment = " + grade10Enrolment +
//                ", \ngrade11Enrolment = " + grade11Enrolment +
//                ", \ngrade12Enrolment = " + grade12Enrolment +
//                "\n}";
//    }
}
