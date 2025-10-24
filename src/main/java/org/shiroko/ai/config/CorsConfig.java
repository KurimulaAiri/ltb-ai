package org.shiroko.ai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有接口生效（/**表示匹配所有路径）
                .allowedOrigins("http://localhost:8081", "https://c.kuaidimao.com") // 允许的源（多个用逗号分隔）
                .allowedOriginPatterns("https://*.kuaidimao.com") // 允许的源（多个用逗号分隔，支持通配符）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法（包括预检请求OPTIONS）
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true) // 允许携带Cookie
                .maxAge(3600); // 预检请求缓存1小时（3600秒）
    }

}
