package com.hjhotelback.utils;

import org.springframework.http.ResponseCookie;

public class JwtCookieUtils {

    private JwtCookieUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static ResponseCookie createJwtToken(String jwt) {
        return ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                //.secure(true)  // HTTPS 환경에서 true로 설정
                .path("/")
                .maxAge(24 * 60 * 60)  // 1일
                .sameSite("Strict")
                .build();
    }

    public static ResponseCookie deleteJwtToken() {
        return ResponseCookie.from("jwt", "")
                .httpOnly(true)
                //.secure(true)  // HTTPS 환경에서 true로 설정
                .path("/")
                .maxAge(0)  // 즉시 삭제
                .sameSite("Strict")
                .build();
    }
}
