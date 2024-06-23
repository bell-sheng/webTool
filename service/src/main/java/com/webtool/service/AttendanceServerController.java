package com.webtool.service;


import cn.hutool.core.io.FileUtil;
import com.webtool.api.AttendanceServerApi;
import com.webtool.service.attendance.AttendanceRequest;
import com.webtool.service.attendance.AttendanceResponse;
import com.webtool.service.attendance.AttendanceService;
import com.webtool.service.attendance.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@Slf4j
public class AttendanceServerController implements AttendanceServerApi {
    private static final String TEMP_ROOT_PATH = "D:\\learn\\workspace\\temp\\";

    @Autowired
    private AttendanceService attendanceService;

    @Override
    public ResponseEntity<Object> processOriginData(MultipartFile originFile, Integer needYear, Integer needMonth) {
        String originFileName = TEMP_ROOT_PATH + "origin_" + new Date().getTime() + ".dat";
        try {
            byte[] bytes = originFile.getBytes();
            FileUtil.writeBytes(bytes, originFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String tempResultFileName = "result_" + new Date().getTime() + ".xls";
        String tempResultFilePath = TEMP_ROOT_PATH + tempResultFileName;
        AttendanceRequest attendanceRequest = new AttendanceRequest();
        attendanceRequest.setYear(needYear);
        attendanceRequest.setMonth(needMonth);
        attendanceRequest.setDestFileName(tempResultFilePath);
        attendanceRequest.setOriginFileName(originFileName);
        AttendanceResponse attendanceResponse = attendanceService.processOriginData(attendanceRequest);
        ExcelUtils.writeToExcel(tempResultFilePath, attendanceResponse.getData());
        Resource resource = new PathResource(tempResultFilePath);
        HttpHeaders headers = getHttpHeadersByFile(tempResultFileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(resource);
    }

    private HttpHeaders getHttpHeadersByFile(String tempResultFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + tempResultFile);
        return headers;
    }
}
