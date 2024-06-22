package com.webtool.service.attendance;

import lombok.Data;

import java.util.List;

@Data
public class AttendanceResponse {
    private String code;
    private String message;
    private List<AttendanceData> data;
}
