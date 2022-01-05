package com.osakakuma.opms.util;

import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.error.OpmsException;
import com.osakakuma.opms.error.OpmsUnauthorizedException;

import java.util.Objects;
import java.util.function.Supplier;

public class OpmsAssert {
    public static void isTrue(boolean condition, Supplier<String> message) {
        if(!condition) {
            throw new OpmsException(message.get());
        }
    }

    public static void authorize(CognitoUser obj) {
        authorize(obj, () -> "Unauthorized request to OPMS backend system");
    }

    public static void authorize(Object obj, Supplier<String> message) {
        if (Objects.isNull(obj)) {
            throw new OpmsUnauthorizedException(message.get());
        }
    }
}
