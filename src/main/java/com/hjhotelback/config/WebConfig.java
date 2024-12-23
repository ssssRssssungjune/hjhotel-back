package com.hjhotelback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// cors를 스프링 시큐리티로 통합하기 위해 비활성화함.
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000", "http://hotel-api", "http://cofile.co.kr") // 프론트엔드 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowCredentials(true) // 쿠키 및 인증정보 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .exposedHeaders("Set-Cookie"); // 응답 헤더 노출
    }
}
