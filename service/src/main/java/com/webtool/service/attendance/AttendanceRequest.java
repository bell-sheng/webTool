package com.webtool.service.attendance;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AttendanceRequest {
    @Value("${attendance.config.originFileName}")
    private String originFileName;
    @Value("${attendance.config.destFileName}")
    private String destFileName;
    @Value("${attendance.config.year}")
    private Integer year;
    @Value("${attendance.config.month}")
    private Integer month;
}
