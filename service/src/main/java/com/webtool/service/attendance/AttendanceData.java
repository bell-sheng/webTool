package com.webtool.service.attendance;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AttendanceData {
    private Integer id;
    private String dateFormat;
    private AttendanceStatus attendanceStatus;
}
