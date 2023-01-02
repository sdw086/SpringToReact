package com.example.reactboot.moduls.api;

import com.example.reactboot.common.annotation.Noheader;
import com.example.reactboot.common.response.JsonResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Token", description = "Token 만들기")
@RestController
@RequestMapping(value = "/api")
public class TokenController {
    @Value("${api.token.issuer}")
    private String API_TOKEN_ISSUER;
    @Value("${api.token.typ}")
    private String API_TOKEN_TYP;
    @Value("${api.token.alg}")
    private String API_TOKEN_ALG;
    @Value("${api.token.api-key}")
    private String API_TOKEN_API_KEY;
    @Value("${api.token.secret-key}")
    private String API_TOKEN_SECRET_KEY;
    @Value("${api.token.expire}")
    private String API_TOKEN_EXPIRE;

    @Noheader
    @Operation(description = "JWT token 생성")
    @PostMapping(value = "/token")
    public JsonResponse<String> createToken(
            @Parameter(description = "APIKey", name = "apiKey", required = true) @RequestParam(value = "apiKey", defaultValue = "") String apiKey
    ) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(apiKey)) {
            return JsonResponse.error("apikey가 없습니다.");
        } else if (!apiKey.equals(API_TOKEN_API_KEY)) {
            return JsonResponse.error("apikey가 일치하지 않습니다.");
        }

        Date now                        = new Date();
        long validityInMilliseconds     = Long.parseLong(API_TOKEN_EXPIRE);
        long nonce                      = Long.valueOf(new Date().getTime()), expTime = validityInMilliseconds;   // 유효 시간 2H, 학습 사이트 특성 고려

        try {
            if (nonce < now.getTime() - expTime)
                return JsonResponse.error("nonce값이 유효하지 않습니다...");
        } catch (Exception e) {
            return JsonResponse.error("nonce값은 millisecond값으로 설정해야 합니다.");
        }

        SecretKey key                   = Keys.hmacShaKeyFor(API_TOKEN_SECRET_KEY.getBytes("UTF-8"));

        Map<String, Object> headers     = new HashMap<>();
        headers.put("typ"               , API_TOKEN_TYP);
        headers.put("alg"               , API_TOKEN_ALG);

        Claims claims                   = Jwts.claims().setSubject("admin");
        claims.put("apiKey"             , apiKey);


        String jwt = Jwts.builder()
                .setHeader(headers)
                .setIssuer(API_TOKEN_ISSUER)
                .setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(new Date(nonce + expTime))
                .signWith(key)
                .compact();

        return JsonResponse.ok("토큰이 생성되었습니다.", jwt);
    }
}
