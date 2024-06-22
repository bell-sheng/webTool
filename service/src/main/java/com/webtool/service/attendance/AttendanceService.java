package com.webtool.service.attendance;

public interface AttendanceService {
    /**
     * 处理原始数据
     *
     * @param attendanceRequest 请求
     * @return 处理后的结果
     */
    AttendanceResponse processOriginData(AttendanceRequest attendanceRequest);
}
