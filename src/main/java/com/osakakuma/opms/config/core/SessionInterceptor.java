package com.osakakuma.opms.config.core;

import com.osakakuma.opms.config.model.CognitoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
    static final String SESSION_USER = "session_user";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var user = getCognitoUser(request);



        return true;
    }

    private CognitoUser getCognitoUser(HttpServletRequest request) {

        return null;
    }
}
