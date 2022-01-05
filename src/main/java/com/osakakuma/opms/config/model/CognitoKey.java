package com.osakakuma.opms.config.model;

/**
 * <ul>
 *     <li>The kid is a hint that indicates which key was used to secure the JSON web signature (JWS) of the token.</li>
 *     <li>The alg header parameter represents the cryptographic algorithm that is used to secure the ID token.
 *     User pools use an RS256 cryptographic algorithm, which is an RSA signature with SHA-256.</li>
 *     <li>The kty parameter identifies the cryptographic algorithm family that is used with the key, such as "RSA" in this example.</li>
 *     <li>The e parameter contains the exponent value for the RSA public key. It is represented as a Base64urlUInt-encoded value.</li>
 *     <li>The n parameter contains the modulus value for the RSA public key. It is represented as a Base64urlUInt-encoded value.</li>
 *     <li>The use parameter describes the intended use of the public key. For this example, the use value sig represents signature.</li>
 * </ul>
 */
public record CognitoKey(
    String kid,
    String alg,
    String kty,
    String e,
    String n,
    String use
) { }
