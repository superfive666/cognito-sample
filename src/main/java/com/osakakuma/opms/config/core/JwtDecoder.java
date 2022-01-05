package com.osakakuma.opms.config.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osakakuma.opms.config.SecurityConfig;
import com.osakakuma.opms.config.model.CognitoUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtDecoder {
   private final SecurityConfig securityConfig;
   private final ObjectMapper objectMapper;

   private JWTVerifier jwtVerifier;

   @PostConstruct
   public void initDecoder() {
        var algorithm = Algorithm.RSA256(securityConfig);
        this.jwtVerifier = Optional.ofNullable(securityConfig.getAudience())
                                .map(audience -> JWT.require(algorithm).withAudience(audience).build())
                                        .orElse(JWT.require(algorithm).build());
   }

   @SneakyThrows(JsonProcessingException.class)
   public CognitoUser verifyToken(String token) {
        var decoded = jwtVerifier.verify(token);
        var payload = new String(Base64.getDecoder().decode(decoded.getPayload()));
        return objectMapper.readValue(payload, CognitoUser.class);
   }
}
