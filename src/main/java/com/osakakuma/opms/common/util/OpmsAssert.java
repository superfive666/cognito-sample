package com.osakakuma.opms.common.util;

import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.error.OpmsUnauthorizedException;
import com.osakakuma.opms.error.OpmsValidationException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Supplier;

public class OpmsAssert {
    public static void isTrue(boolean condition, Supplier<String> message) {
        if(!condition) {
            throw new OpmsValidationException(message.get());
        }
    }

    public static void equalsIgnoreCase(String left, String right, Supplier<String> message) {
        if (!StringUtils.equalsIgnoreCase(left, right)) {
            throw new OpmsValidationException(message.get());
        }
    }

    public static void authorize(CognitoUser obj) {
        authorize(obj, () -> "Unauthorized request to OPMS backend system");
    }

    public static void authorize(boolean condition, Supplier<String> message) {
        if (!condition) {
            throw new OpmsUnauthorizedException(message.get());
        }
    }

    public static void authorize(Object obj, Supplier<String> message) {
        if (Objects.isNull(obj)) {
            throw new OpmsUnauthorizedException(message.get());
        }
    }
}
