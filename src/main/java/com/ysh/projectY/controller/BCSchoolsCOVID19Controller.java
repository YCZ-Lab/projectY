package com.ysh.projectY.controller;

import com.ysh.projectY.entity.BCSchoolsCOVID19;
import com.ysh.projectY.form.*;
import com.ysh.projectY.service.BCSchoolsCOVID19Service;
import com.ysh.projectY.utils.JsonResponse;
import com.ysh.projectY.utils.MethodResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "${projectY.api-base-path}")
public class BCSchoolsCOVID19Controller {

    final BCSchoolsCOVID19Service bcSchoolsCOVID19Service;

    public BCSchoolsCOVID19Controller(BCSchoolsCOVID19Service bcSchoolsCOVID19Service) {
        this.bcSchoolsCOVID19Service = bcSchoolsCOVID19Service;
    }

    // 匿名用户可以访问
    @GetMapping("/public/bcSchoolsCOVID19")
    public HttpEntity<?> getBCSchoolsCOVID19(@RequestParam(name = "schoolName", defaultValue = "") String schoolName,
                                             @RequestParam(name = "notificationDate", defaultValue = "") String notificationDate,
                                             @RequestParam(name = "districtAbb", defaultValue = "") String districtAbb,
                                             @RequestParam(name = "notificationMethod", defaultValue = "") String notificationMethod,
                                             @RequestParam(name = "healthRegionName", defaultValue = "") String healthRegionName,
                                             @RequestParam(name = "cityName", defaultValue = "") String cityName,
                                             @RequestParam(name = "current", defaultValue = "1") int current,
                                             @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        if (current <= 0) {
            current = 1;
        }
        if (pageSize <= 0) {
            pageSize = 1;
        }
        if (pageSize > 1000) {
            pageSize = 1000;
        }
        System.out.println(notificationDate);
        if (!"".equals(notificationDate) && notificationDate.length() >= 10) {
            notificationDate = notificationDate.substring(0, 10);
        }
        Pageable pageable = PageRequest.of(current - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        final Page<BCSchoolsCOVID19> bcSchoolsCOVID19 = bcSchoolsCOVID19Service.getBCSchoolsCOVID19(schoolName, notificationDate, districtAbb, notificationMethod, healthRegionName, cityName, pageable);
//        Map<String, Object> temp = new HashMap<>();
//        temp.put("bcSchoolsCOVID19DataType", bcSchoolsCOVID19);
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), "projectY.BCSchoolsCOVID19Controller.getBCSchoolsCOVID19.success", bcSchoolsCOVID19, "bcSchoolsCOVID19.toString()"), HttpStatus.OK);
    }


    // 匿名用户可以访问
    @GetMapping("/public/bcSchoolsDistrictsCOVID19Summary")
    public HttpEntity<?> getBCSchoolsDistrictsCOVID19Summary(@RequestParam(name = "startDate", defaultValue = "2021-01-01") String startDate,
                                                             @RequestParam(name = "endDate", defaultValue = "2021-12-31") String endDate) {
        final Set<BCSchoolsDistrictsCOVID19Summary> bcSchoolsDistrictsCOVID19 = bcSchoolsCOVID19Service.getBCSchoolsDistrictsCOVID19Summary(startDate, endDate);
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), "projectY.BCSchoolsCOVID19Controller.getBCSchoolsDistrictsCOVID19.success", bcSchoolsDistrictsCOVID19, "!!!"), HttpStatus.OK);
    }

    // 匿名用户可以访问
    @GetMapping("/public/bcSchoolsCOVID19Summary")
    public HttpEntity<?> getBCSchoolsCOVID19Summary(@RequestParam(name = "startDate", defaultValue = "2021-01-01") String startDate,
                                                    @RequestParam(name = "endDate", defaultValue = "2021-12-31") String endDate) {
        final Set<BCSchoolsCOVID19Summary> bcSchoolsCOVID19 = bcSchoolsCOVID19Service.getBCSchoolsCOVID19Summary(startDate, endDate);
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), "projectY.BCSchoolsCOVID19Controller.getBCSchoolsCOVID19Summary.success", bcSchoolsCOVID19, "!!!"), HttpStatus.OK);
    }

    // 匿名用户可以访问
    @GetMapping("/public/bcSchoolsHealthsCOVID19Summary")
    public HttpEntity<?> getBCSchoolsHealthsCOVID19Summary(@RequestParam(name = "startDate", defaultValue = "2021-01-01") String startDate,
                                                           @RequestParam(name = "endDate", defaultValue = "2021-12-31") String endDate) {
        final Set<BCSchoolsHealthsCOVID19Summary> bcSchoolsHealthsCOVID19 = bcSchoolsCOVID19Service.getBCSchoolsHealthsCOVID19Summary(startDate, endDate);
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), "projectY.BCSchoolsCOVID19Controller.getBCSchoolsHealthsCOVID19Summary.success", bcSchoolsHealthsCOVID19, "!!!"), HttpStatus.OK);
    }

    // 匿名用户可以访问
    @GetMapping("/public/bcSchoolsCOVID19TotalSummary")
    public HttpEntity<?> getBCSchoolsCOVID19TotalSummary() {
        final Optional<BCSchoolsCOVID19TotalSummary> bcSchoolsCOVID19TotalSummary = bcSchoolsCOVID19Service.getBCSchoolsCOVID19TotalSummary();
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), "projectY.BCSchoolsCOVID19Controller.getBCCOVID19TotalSummary.success", bcSchoolsCOVID19TotalSummary.get(), "!!!"), HttpStatus.OK);
    }

    // 匿名用户可以访问
    @GetMapping("/public/bcSchoolsCOVID19DailySummary")
    public HttpEntity<?> getBCSchoolsCOVID19DailySummary(@RequestParam(name = "startDate", defaultValue = "2021-01-01") String startDate,
                                                         @RequestParam(name = "endDate", defaultValue = "2021-12-31") String endDate) {
        final Set<BCSchoolsCOVID19DailySummary> bcSchoolsCOVID19DailySummary = bcSchoolsCOVID19Service.getBCSchoolsCOVID19DailySummary(startDate, endDate);
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), "projectY.BCSchoolsCOVID19Controller.getBCSchoolsCOVID19DailySummary.success", bcSchoolsCOVID19DailySummary, "!!!"), HttpStatus.OK);
    }

    // 匿名用户可以访问
    @GetMapping("/public/bcSchoolsHealthsCitiesCOVID19Summary")
    public HttpEntity<?> getBCSchoolsHealthsCitiesCOVID19Summary(@RequestParam(name = "startDate", defaultValue = "2021-01-01") String startDate,
                                                                 @RequestParam(name = "endDate", defaultValue = "2021-12-31") String endDate) {
        final Set<BCSchoolsHealthsCitiesCOVID19Summary> bcSchoolsHealthsCitiesCOVID19 = bcSchoolsCOVID19Service.getBCSchoolsHealthsCitiesCOVID19Summary(startDate, endDate);
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), "projectY.BCSchoolsCOVID19Controller.getBCSchoolsHealthsCitiesCOVID19Summary.success", bcSchoolsHealthsCitiesCOVID19, "!!!"), HttpStatus.OK);
    }

    // 匿名用户可以访问
    @GetMapping("/public/bcSchoolsCOVID19MonthlySummary")
    public HttpEntity<?> getBCSchoolsCOVID19MonthlySummary(@RequestParam(name = "startDate", defaultValue = "2020-01-01") String startDate,
                                                           @RequestParam(name = "endDate", defaultValue = "2021-12-31") String endDate) {
        final Set<BCSchoolsCOVID19MonthlySummary> bcSchoolsCOVID19MonthlySummary = bcSchoolsCOVID19Service.getBCSchoolsCOVID19MonthlySummary(startDate, endDate);
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), "projectY.BCSchoolsCOVID19Controller.getBCCOVID19MonthlySummary.success", bcSchoolsCOVID19MonthlySummary, "!!!"), HttpStatus.OK);
    }

    // 匿名用户可以访问, 统计数量太少,没有用
    @GetMapping("/public/bcSchoolCOVID19DailySummary")
    public HttpEntity<?> getBCSchoolCOVID19DailySummary(@RequestParam(name = "startDate", defaultValue = "2020-01-01") String startDate,
                                                        @RequestParam(name = "endDate", defaultValue = "2021-12-31") String endDate,
                                                        @RequestParam(name = "schoolId") int schoolId) {
        final Set<BCSchoolCOVID19DailySummary> bcSchoolCOVID19DailySummary = bcSchoolsCOVID19Service.getBCSchoolCOVID19DailySummary(startDate, endDate, schoolId);
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), "projectY.BCSchoolsCOVID19Controller.getBCSchoolCOVID19DailySummary.success", bcSchoolCOVID19DailySummary, "!!!"), HttpStatus.OK);
    }

    @GetMapping("/public/bcSchoolsCOVID19GoogleMapSummary")
    public HttpEntity<?> getBCSchoolsCOVID19GoogleMapSummary() {
        final MethodResponse methodResponse = bcSchoolsCOVID19Service.getBCSchoolsCOVID19GoogleMapSummary();
        if (!methodResponse.isSuccess()) {
            return new ResponseEntity<>(JsonResponse.failure(HttpStatus.UNPROCESSABLE_ENTITY.value(), methodResponse.getI18nMessageKey(), methodResponse.getData(), methodResponse.getDetail()), HttpStatus.OK);
        }
        return new ResponseEntity<>(JsonResponse.success(HttpStatus.OK.value(), methodResponse.getI18nMessageKey(), methodResponse.getData(), methodResponse.getDetail()), HttpStatus.OK);
    }
}
