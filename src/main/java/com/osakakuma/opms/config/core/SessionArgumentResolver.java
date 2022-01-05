package com.osakakuma.opms.config.core;

import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.util.OpmsAssert;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SessionArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        var type = parameter.getParameterType();
        return CognitoUser.class.equals(type);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var user = (CognitoUser) webRequest.getAttribute(SessionInterceptor.SESSION_USER, RequestAttributes.SCOPE_REQUEST);
        OpmsAssert.authorize(user);

        return user;
    }
}
