package com.osakakuma.opms.config;

import com.osakakuma.opms.config.model.CognitoJwk;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "opms.security")
public class SecurityConfig {
    /**
     * The default AWS region where the cognito user pool is deployed to.
     * If multiple AWS services are used, there can be different aws regions configured, but this value should
     * be corresponding to the region ID that cognito service resides in.
     */
    private String awsRegion;
    /**
     * Cognito user pool ID is found in the manage user pool page.
     * Under the tab of "General Settings" information page
     */
    private String cognitoPoolId;
    /**
     * This attribute is initialized at server start up only once, to pull from AWS the latest
     * cognito jwk public key for verification purpose
     */
    private CognitoJwk cognitoJwk;

    /**
     * Download and store the corresponding public JSON Web Key (JWK) for your user pool.
     * It is available as part of a JSON Web Key Set (JWKS).
     * You can locate it by constructing the following URL for your environment:
     * https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json
     */
    @PostConstruct
    public void pullAwsCognitoKeys() {
        var restTemplate = new RestTemplate();
        var url = MessageFormat.format(
                "https://cognito-idp.{0}.amazonaws.com/{1}/.well-known/jwks.json",
                awsRegion, cognitoPoolId);

        this.cognitoJwk = restTemplate.getForObject(url, CognitoJwk.class);
    }
}
