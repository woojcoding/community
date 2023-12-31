package com.portfolio.communityuser.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWT토큰을 encode, decode 해주는 유틸입니다.
 */
@Component
public class JwtUtil {
    /**
     * 알고리즘
     */
    private final Algorithm algorithm;

    /**
     * 암호를 담아 JwtUtil을 생성하는 메서드
     *
     * @param secret 암호
     */
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    /**
     * Jwt 토큰을 생성하는 메서드
     *
     * @param accountId@return String 토큰
     */
    public String encode(String accountId) {
        return JWT.create()
                .withClaim("accountId", accountId)
                .sign(algorithm);
    }

    /**
     * Jwt 토큰을 해독하는 메서드
     *
     * @param token 토큰
     * @return String 유저 Id
     */
    public String decode(String token) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return decodedJWT.getClaim("accountId").asString();
    }
}
