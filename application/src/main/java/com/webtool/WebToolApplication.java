package com.webtool;

import cn.hutool.core.date.DateUtil;
import com.webtool.service.attendance.AttendanceRequest;
import com.webtool.service.attendance.AttendanceResponse;
import com.webtool.service.attendance.AttendanceService;
import com.webtool.service.attendance.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
@Slf4j
public class WebToolApplication {

    @Autowired
    private AttendanceRequest attendanceRequest;

    @Autowired
    private AttendanceService attendanceService;

    public static void main(String[] args) {
        SpringApplication.run(WebToolApplication.class, args);
    }

    @PostConstruct
    public void init() {
        log.info("start to attendance request:{}", attendanceRequest);
        String resultName = "result_" + new Date().getTime() + ".xls";
        log.info("resultName:{}", resultName);
        attendanceRequest.setDestFileName(resultName);
        AttendanceResponse attendanceResponse = attendanceService.processOriginData(attendanceRequest);
        ExcelUtils.writeToExcel(attendanceRequest.getDestFileName(), attendanceResponse.getData());
        log.info("end to attendance.");
    }
}
