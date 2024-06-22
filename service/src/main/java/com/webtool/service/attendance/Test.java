package com.webtool.service.attendance;

import lombok.val;

public class Test {

    public static void main(String[] args) {
        AttendanceRequest attendanceRequest = new AttendanceRequest();
        attendanceRequest.setOriginFileName("X:\\考勤\\AGHO200161216_attlog.dat");
        attendanceRequest.setYear(2024);
        attendanceRequest.setMonth(5);
        attendanceRequest.setDestFileName("X:\\考勤\\G_5月考勤数据.xls");
        AttendanceService attendanceService = new AttendanceServiceImpl();
        val attendanceResponse = attendanceService.processOriginData(attendanceRequest);

        ExcelUtils.writeToExcel(attendanceRequest.getDestFileName(), attendanceResponse.getData());
    }
}
