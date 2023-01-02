package com.example.reactboot.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {

    /**
     * token 생성
     */
    public String createToken(PropertyUtil propertyUtil, String memberId) throws UnsupportedEncodingException {
        /**
         * HEADER 설정
         * 헤더는 typ과 alg 두 가지 정보로 구성된다. alg는 헤더(Header)를 암호화 하는 것이 아니고, Signature를 해싱하기 위한 알고리즘을 지정하는 것이다.
         *  	typ: 토큰의 타입을 지정 ex) JWT
         *     	alg: 알고리즘 방식을 지정하며, 서명(Signature) 및 토큰 검증에 사용 ex) HS256(SHA256) 또는 RSA
         */
        Map<String, Object> headers     = new HashMap<>();
        // type
        headers.put("typ", propertyUtil.getPropery("api.token.typ"));
        // HS256
        headers.put("alg", propertyUtil.getPropery("api.token.alg"));

        /**
         * PAYLOAD 설정
         * 토큰의 페이로드에는 토큰에서 사용할 정보의 조각들인 클레임(Claim)이 담겨 있다. 클레임은 총 3가지로 나누어지며,
         * 		Json(Key/Value) 형태로 다수의 정보를 넣을 수 있다.
         * 		header와 payload에는 기밀정보는 넣으면 안된다.
         */

        // token 유효 시간
        // token 만료
        Date now                        = new Date();

        /**
         * token builder
         * iss: 토큰 발급자(issuer)
         * sub: 토큰 제목(subject)
         * aud: 토큰 대상자(audience)
         * exp: 토큰 만료 시간(expiration), NumericDate 형식으로 되어 있어야 함 ex) 1480849147370
         * nbf: 토큰 활성 날짜(not before), 이 날이 지나기 전의 토큰은 활성화되지 않음
         * iat: 토큰 발급 시간(issued at), 토큰 발급 이후의 경과 시간을 알 수 있음
         * jti: JWT 토큰 식별자(JWT ID), 중복 방지를 위해 사용하며, 일회용 토큰(Access Token) 등에 사용
         *
         * Signature(서명)
         * 서명(Signature)은 토큰을 인코딩하거나 유효성 검증을 할 때 사용하는 고유한 암호화 코드이다.
         * 서명(Signature)은 위에서 만든 헤더(Header)와 페이로드(Payload)의 값을 각각 BASE64로 인코딩하고,
         * 인코딩한 값을 비밀 키를 이용해 헤더(Header)에서 정의한 알고리즘으로 해싱을 하고, 이 값을 다시 BASE64로 인코딩하여 생성한다.
         */
        SecretKey key = Keys.hmacShaKeyFor(propertyUtil.getPropery("api.token.secret-key").getBytes("UTF-8"));
        long validityInMilliseconds = Long.parseLong(propertyUtil.getPropery("api.token.expire"));
        long nonce  = Long.valueOf(new Date().getTime()) , expTime = validityInMilliseconds;   // 유효 시간 2H, 학습 사이트 특성 고려

        Claims claims   = Jwts.claims().setSubject(memberId);
        claims.put("apiKey", propertyUtil.getPropery("api.token.api-key"));
        String jwt  = Jwts.builder()
                .setHeader(headers)                             // header 설정
                .setIssuer(propertyUtil.getPropery("api.token.issuer"))
                .setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(new Date(nonce + expTime))             // token 만료 시간
                .signWith(key)                                  // HS256과 key로 sign
                .compact();                                     // token 생성

        return "Bearer " + jwt;
    }

    /**
     * token refresh
     */
    public String refreshToken(PropertyUtil propertyUtil, String memberId) throws UnsupportedEncodingException {

        Date now                    = new Date();
        SecretKey key               = Keys.hmacShaKeyFor(propertyUtil.getPropery("api.token.secret-key").getBytes("UTF-8"));
        Claims claims               = Jwts.claims().setSubject(memberId);
        claims.put("apiKey", propertyUtil.getPropery("api.token.api-key"));
        long validityInMilliseconds = Long.parseLong(propertyUtil.getPropery("api.token.refresh-expire"));
        long nonce                  = Long.valueOf(new Date().getTime()) , expTime = validityInMilliseconds;   // 유효 시간 2H, 학습 사이트 특성 고려

        String refreshJwt   = Jwts.builder()
                .setIssuer("refresh")
                .setIssuedAt(now)
                .setExpiration(new Date(nonce + expTime))
                .setClaims(claims)
                .signWith(key)
                .compact();

        return "Bearer " + refreshJwt;
    }

    /**
     * token 검증
     */
    public Map<String, Object> verifyJWT(String secretKey, String token) {
        Map<String, Object> claimMap        = new HashMap<>();
        try {
            Claims claims   = (Claims) Jwts.parser()
                    .setSigningKey(secretKey.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();

            claimMap        = claims;
            claimMap.put("memberId", claims.getSubject());
            claimMap.put("result", "notExp");
            return claimMap;

        } catch (ExpiredJwtException e) {   // token 만료 되었을 경우
            claimMap.put("result", "exp");
            return  claimMap;
        }
        catch (Exception e) {
            claimMap.put("result", "error");
            return claimMap;
        } finally {
            System.out.println("fin");
        }
    }
}
