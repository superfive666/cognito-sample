package com.osakakuma.opms.config;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.MessageFormat;

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "opms.security")
public class SecurityConfig implements RSAKeyProvider {
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
     * If audience is provided will add audience
     */
    private String audience;
    /**
     * This attribute is initialized at server start up only once, to pull from AWS the latest
     * cognito jwk public key for verification purpose
     */
    private JwkProvider jwkProvider;

    /**
     * Download and store the corresponding public JSON Web Key (JWK) for your user pool.
     * It is available as part of a JSON Web Key Set (JWKS).
     * You can locate it by constructing the following URL for your environment:
     * https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json
     */
    @PostConstruct
    @SneakyThrows(MalformedURLException.class)
    public void pullAwsCognitoKeys() {
        var url = MessageFormat.format(
                "https://cognito-idp.{0}.amazonaws.com/{1}/.well-known/jwks.json",
                awsRegion, cognitoPoolId);

       this.jwkProvider = new JwkProviderBuilder(new URL(url)).build();
    }

    @Override
    @SneakyThrows(JwkException.class)
    public RSAPublicKey getPublicKeyById(String keyId) {
        return (RSAPublicKey) jwkProvider.get(keyId).getPublicKey();
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }

    @Override
    public String getPrivateKeyId() {
        return null;
    }
}
