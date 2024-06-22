package com.webtool.service.attendance;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ProcessedAttendanceResult {
    private Integer id;
    private Map<String, AttendanceStatus> attendanceStatusMap;
}
