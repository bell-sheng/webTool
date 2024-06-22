package com.webtool.service.attendance;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.webtool.service.attendance.offday.DateInfo;
import com.webtool.service.attendance.offday.OffDayResponse;
import lombok.val;
import org.apache.commons.io.IOUtils;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class DateUtils {

    public static List<DateTime> getAllDateByMonth(Integer year, Integer month) {
        String dateStr = year + "-" + month + "-01";
        return DateUtil.rangeToList(DateUtil.beginOfMonth(DateUtil.parseDate(dateStr)), DateUtil.endOfMonth(DateUtil.parseDate(dateStr)), DateField.DAY_OF_MONTH);
    }

    public static boolean isOffDay(String dateByMouth) {
        // https://api.apihubs.cn/holiday/get?size=500&year=2024 调用该接口查询是否OffDay，目前缓存在json文件中
        InputStream inputStream = DateUtils.class.getClassLoader().getResourceAsStream("2024.json");
        String allDateJson = null;
        try {
            allDateJson = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        val offDayResponse = JSON.parseObject(allDateJson, OffDayResponse.class);
        val dateInfos = offDayResponse.getData().getList();
        DateInfo dateInfo = getDateInfoByDate(dateInfos, dateByMouth);
        return !Objects.nonNull(dateInfo) || dateInfo.getWorkday() != 1;
    }

    private static DateInfo getDateInfoByDate(List<DateInfo> dateInfos, String dateByMouth) {
        val replaceDate = dateByMouth.replace("-", "");
        return dateInfos.stream().filter(dateInfo -> replaceDate.equals(dateInfo.getDate())).findFirst().orElse(null);
    }
}
