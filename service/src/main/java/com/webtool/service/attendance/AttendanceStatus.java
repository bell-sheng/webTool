package com.webtool.service.attendance;

import lombok.Data;

@Data
public class AttendanceStatus {
    private String startTime;
    private String endTime;
    private Boolean isLate;
    private Boolean isEarlier;

    @Override
    public String toString() {
        return startTime + "," + endTime + (isLate ? ",迟到" : "") + (isEarlier ? ",早退" : "");
    }
}
