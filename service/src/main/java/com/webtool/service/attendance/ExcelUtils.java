package com.webtool.service.attendance;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.val;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExcelUtils {
    private static final int COLUMN_NUM = 2;

    private static final List<String> WEEK_NAMES = Arrays.asList("周日", "周一", "周二", "周三", "周四", "周五", "周六");

    public static void writeToExcel(String destFileName, List<AttendanceData> attendanceDataList) {
        List<Integer> setIds = getAllIds(attendanceDataList);
        List<String> allDateByMouth = getAllDateByMonth(attendanceDataList);

        try (ExcelWriter writer = new ExcelWriter(new File(destFileName), "数据表")) {
            // 写表头
            writer.writeCellValue(0, 1, "工号");
            writer.writeCellValue(1, 1, "姓名");
            Sheet sheet = writer.getSheet();

            for (int index = 0; index < allDateByMouth.size(); index++) {
                int dateColStartIndex = index * 4 + COLUMN_NUM;
                CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, dateColStartIndex, index * 4 + 3 + COLUMN_NUM);
                sheet.addMergedRegion(cellAddresses);
                val dateByMouth = allDateByMouth.get(index);
                writer.writeCellValue(dateColStartIndex, 0, dateByMouth + getWeek(dateByMouth));

                CellStyle cellStyle = getDateTitleStyle(sheet.getWorkbook(), dateByMouth);
                val sheetRow = sheet.getRow(0);
                val dateCell = sheetRow.getCell(dateColStartIndex);
                dateCell.setCellStyle(cellStyle);

                writer.writeCellValue(dateColStartIndex, 1, "上班");
                writer.writeCellValue(index * 4 + 1 + COLUMN_NUM, 1, "下班");
                writer.writeCellValue(index * 4 + 2 + COLUMN_NUM, 1, "迟到");
                writer.writeCellValue(index * 4 + 3 + COLUMN_NUM, 1, "早退");
            }

            // 写数据
            for (Integer id : setIds) {
                int index_y = setIds.indexOf(id) + 2;
                writer.writeCellValue(0, index_y, id);
                writer.writeCellValue(1, index_y, "");
                for (String date : allDateByMouth) {
                    int index_x = allDateByMouth.indexOf(date);
                    AttendanceStatus attendanceStatus = getAttendanceData(id, date, attendanceDataList);
                    if (Objects.isNull(attendanceStatus)) {
                        continue;
                    }
                    writer.writeCellValue(index_x * 4 + COLUMN_NUM, index_y, attendanceStatus.getStartTime());
                    writer.writeCellValue(index_x * 4 + 1 + COLUMN_NUM, index_y, attendanceStatus.getEndTime());
                    writer.writeCellValue(index_x * 4 + 2 + COLUMN_NUM, index_y, attendanceStatus.getIsLate() ? "迟到" : "");
                    writer.writeCellValue(index_x * 4 + 3 + COLUMN_NUM, index_y, attendanceStatus.getIsEarlier() ? "早退" : "");
                }
            }
        }
    }

    private static String getWeek(String dateByMouth) {
        StringBuilder weekStringBuilder = new StringBuilder();
        weekStringBuilder.append("（");
        val dateTime = DateUtil.parseDate(dateByMouth);
        val dayOfWeek = dateTime.dayOfWeek();
        weekStringBuilder.append(WEEK_NAMES.get(dayOfWeek - 1));
        weekStringBuilder.append("）");
        return weekStringBuilder.toString();
    }

    private static CellStyle getDateTitleStyle(Workbook workbook, String dateByMouth) {
        val cellStyle = workbook.createCellStyle();
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setFillForegroundColor(DateUtils.isOffDay(dateByMouth) ? IndexedColors.GREEN.getIndex() : IndexedColors.RED.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private static AttendanceStatus getAttendanceData(Integer id, String date, List<AttendanceData> attendanceDataList) {
        return attendanceDataList.stream().filter(attendanceData -> id.equals(attendanceData.getId()) && date.equals(attendanceData.getDateFormat())).findFirst().map(AttendanceData::getAttendanceStatus).orElse(null);
    }

    private static List<Integer> getAllIds(List<AttendanceData> attendanceDataList) {
        List<Integer> setIds = new ArrayList<>();
        attendanceDataList.forEach(attendanceData -> {
            if (!setIds.contains(attendanceData.getId())) {
                setIds.add(attendanceData.getId());
            }
        });
        return setIds;
    }

    private static List<String> getAllDateByMonth(List<AttendanceData> attendanceDataList) {
        List<String> allFormatDate = new ArrayList<>();
        attendanceDataList.forEach(attendanceData -> {
            if (!allFormatDate.contains(attendanceData.getDateFormat())) {
                allFormatDate.add(attendanceData.getDateFormat());
            }
        });
        return allFormatDate;
    }
}
