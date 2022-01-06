package com.osakakuma.opms.config.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.osakakuma.opms.common.util.OpmsAssert;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public record CognitoUser (
        @JsonProperty("at_hash") String atHash,
        @JsonProperty("sub") String sub,
        @JsonProperty("cognito:groups") List<String> groups,
        @JsonProperty("iss") String iss,
        @JsonProperty("cognito:username") String username,
        @JsonProperty("aud") String aud,
        @JsonProperty("event_id") String eventId,
        @JsonProperty("token_use") String tokenUse,
        @JsonProperty("auth_time") Long authTime,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("exp") Long exp,
        @JsonProperty("iat") Long iat,
        @JsonProperty("jti") String jti,
        @JsonProperty("email") String email
) {
    public CognitoUser {
        OpmsAssert.isTrue(StringUtils.isNotBlank(username), () -> "Login user without username");
    }
}
