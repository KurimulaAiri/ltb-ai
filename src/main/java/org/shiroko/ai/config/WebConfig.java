package org.shiroko.ai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.shiroko.ai.interceptor.TokenInterceptor;
import org.shiroko.ai.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtUtils jwtUtils;

    private final ObjectMapper objectMapper;

    @Autowired
    public WebConfig(JwtUtils jwtUtils, ObjectMapper objectMapper) {
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor(jwtUtils, objectMapper);  // 注入拦截器
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor()) // 添加拦截器
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns("/admin/login")  // 排除登录接口（无需鉴权）
                .excludePathPatterns("/register");  // 排除注册接口（按需添加）
    }
}