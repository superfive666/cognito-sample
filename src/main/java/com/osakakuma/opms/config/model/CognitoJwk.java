package com.osakakuma.opms.config.model;

import java.util.List;

/**
 * The issuer signature is derived from the public key (the RSA modulus "n") of the kid in
 * jwks.json that matches the token kid.
 * You might need to convert the JWK to PEM format first.
 */
public record CognitoJwk (
    List<CognitoKey> keys
) { }
