package com.webtool.service.attendance.offday;

import lombok.Data;

@Data
public class OffDayResponse {
    private Integer code;
    private String msg;
    private ResponseDataList data;
}
