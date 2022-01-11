package com.osakakuma.opms.common.service;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileUploadServiceTest {

    @Test
    public void test() {
        var format = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault());
        var res= format.format(Instant.now());
        System.out.println(res);
    }
}