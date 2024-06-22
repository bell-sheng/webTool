package com.webtool.service.attendance;

import cn.hutool.core.date.DateUtil;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final static String START_TIME = "09:30:00";
    private final static String END_TIME = "18:00:00";


    /**
     * 处理原始数据
     *
     * @param attendanceRequest 请求
     * @return 处理后的结果
     */
    @Override
    public AttendanceResponse processOriginData(AttendanceRequest attendanceRequest) {
        List<OriginAttendanceResult> originAttendanceResults = readOriginData(attendanceRequest.getOriginFileName());
        Map<Integer, List<OriginAttendanceResult>> sortedResult = new LinkedHashMap<>();
        originAttendanceResults.stream()
                .collect(Collectors.groupingBy(OriginAttendanceResult::getId))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(each -> sortedResult.put(each.getKey(), each.getValue()));

        List<ProcessedAttendanceResult> allProcessedAttendanceResults = new ArrayList<>();
        sortedResult.forEach((attendanceId, attendanceResult) -> {
            Map<String, AttendanceStatus> personalStatus = processAttendanceDate(attendanceResult);
            ProcessedAttendanceResult processedAttendanceResult = new ProcessedAttendanceResult();
            processedAttendanceResult.setId(attendanceId);
            processedAttendanceResult.setAttendanceStatusMap(personalStatus);
            allProcessedAttendanceResults.add(processedAttendanceResult);
        });

        val attendanceResponse = new AttendanceResponse();
        val allDateByMonth = DateUtils.getAllDateByMonth(attendanceRequest.getYear(), attendanceRequest.getMonth());
        List<AttendanceData> attendanceDataList = new ArrayList<>();
        allDateByMonth.forEach(dateTime -> {
            val formatDate = DateUtil.formatDate(dateTime);
            allProcessedAttendanceResults.forEach(processedAttendanceResult -> {
                val attendanceStatusMap = processedAttendanceResult.getAttendanceStatusMap();
                val attendanceStatus = attendanceStatusMap.get(formatDate);
                AttendanceData attendanceData = AttendanceData.builder().id(processedAttendanceResult.getId()).dateFormat(formatDate).attendanceStatus(attendanceStatus).build();
                attendanceDataList.add(attendanceData);
            });
        });
        attendanceResponse.setCode("200");
        attendanceResponse.setMessage("success");
        attendanceResponse.setData(attendanceDataList);
        return attendanceResponse;
    }

    private Map<String, AttendanceStatus> processAttendanceDate(List<OriginAttendanceResult> attendanceResult) {
        Map<String, List<String>> stringListMap = new LinkedHashMap<>();
        for (OriginAttendanceResult originAttendanceResult : attendanceResult) {
            val formatDate = DateUtil.formatDate(originAttendanceResult.getDate());
            val formatTime = DateUtil.formatTime(originAttendanceResult.getDate());
            if (!stringListMap.containsKey(formatDate)) {
                stringListMap.put(formatDate, new ArrayList<>());
            }
            stringListMap.get(formatDate).add(formatTime);
        }

        Map<String, AttendanceStatus> result = new LinkedHashMap<>();
        stringListMap.forEach((date, times) -> {
            AttendanceStatus attendanceStatus = transAttendanceStatus(times);
            result.put(date, attendanceStatus);
        });
        return result;
    }

    private AttendanceStatus transAttendanceStatus(List<String> times) {
        val attendanceStatus = new AttendanceStatus();
        Collections.sort(times);
        val statTime = times.get(0);
        val endTime = times.get(times.size() - 1);

        attendanceStatus.setStartTime(statTime);
        attendanceStatus.setEndTime(endTime);
        attendanceStatus.setIsLate(isLate(statTime, endTime));
        attendanceStatus.setIsEarlier(isEarlier(statTime, endTime));
        return attendanceStatus;
    }

    private boolean isEarlier(String statTime, String endTime) {
        return endTime.compareTo(END_TIME) < 0;
    }

    private boolean isLate(String statTime, String endTime) {
        return statTime.compareTo(START_TIME) > 0;
    }


    public List<OriginAttendanceResult> readOriginData(String filePath) {
        List<OriginAttendanceResult> results = new ArrayList<>();
        try {
            List<String> attendanceResult = Files.readAllLines(new File(filePath).toPath());
            for (String line : attendanceResult) {
                val split = line.split("\t");
                OriginAttendanceResult originAttendanceResult = new OriginAttendanceResult();
                originAttendanceResult.setId(Integer.parseInt(split[0].trim()));
                originAttendanceResult.setDate(DateUtil.parse(split[1].trim()));
                results.add(originAttendanceResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
