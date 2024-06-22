package com.webtool.service.attendance;

import lombok.Data;

@Data
public class AttendanceRequest {
    private String originFileName;
    private String destFileName;
    private Integer year;
    private Integer month;
}
