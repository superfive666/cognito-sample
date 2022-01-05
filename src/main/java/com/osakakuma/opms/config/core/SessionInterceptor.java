package com.osakakuma.opms.config.core;

import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.error.OpmsUnauthorizedException;
import com.osakakuma.opms.util.OpmsAssert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {
    static final String SESSION_USER = "session_user";

    private final JwtDecoder jwtDecoder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var user = getCognitoUser(request);
        request.setAttribute(SESSION_USER, user);
        return Objects.nonNull(user);
    }

    private CognitoUser getCognitoUser(HttpServletRequest request) {
        var jwt = request.getHeader("authorization");
        OpmsAssert.authorize(jwt, () -> "Missing authentication tokens");
        try {
            return resolveJwt(jwt);
        } catch (Exception ex) {
            log.error("Invalid JWT token received");
            throw new OpmsUnauthorizedException("Invalid JWT token");
        }
    }

    private CognitoUser resolveJwt(String jwt) {
        return jwtDecoder.verifyToken(jwt);
    }
}
