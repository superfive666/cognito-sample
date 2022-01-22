package com.osakakuma.opms.common.util;

import com.osakakuma.opms.config.model.CognitoUser;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogBox {
    private final JdbcTemplate jdbcTemplate;
    private final CognitoUser cognitoUser;

    public void log(String title, String before, String after) {

      

    } 
}
