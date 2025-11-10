package org.shiroko.ai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.shiroko.ai.interceptor.TokenInterceptor;
import org.shiroko.ai.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    /**
     * 配置拦截器
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor()) // 添加拦截器
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns("/admin/login")  // 排除登录接口（无需鉴权）
                .excludePathPatterns("/admin/getPublicKey")  // 排除获取公钥接口（无需鉴权）
                .excludePathPatterns("/auth/get_access_token")  // 排除所有 /auth/** 路径
                .excludePathPatterns("/problem_record/add")
                .excludePathPatterns("/register");  // 排除注册接口（按需添加）
    }

    /**
     * 配置跨域请求
     * @param registry 跨域注册器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有接口生效（/**表示匹配所有路径）
                .allowedOrigins("http://localhost:8081", "http://localhost:25502") // 允许的源（多个用逗号分隔）
                .allowedOriginPatterns("https://*.kuaidimao.com", "https://*.wyhlife.com", "http://localhost:25502") // 允许的源（多个用逗号分隔，支持通配符）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法（包括预检请求OPTIONS）
                .allowedHeaders("Authorization", "Content-Type") // 允许的请求头
                .allowCredentials(true) // 允许携带Cookie
                .maxAge(3600); // 预检请求缓存1小时（3600秒）
    }
}