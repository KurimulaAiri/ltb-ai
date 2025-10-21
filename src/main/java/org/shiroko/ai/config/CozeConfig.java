package org.shiroko.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Coze 配置类（从 application.yml 读取配置）
 */
@Configuration
@ConfigurationProperties(prefix = "coze")  // 绑定配置文件中 "coze" 前缀的属性
@Data
public class CozeConfig {
    // API 基础域名
    private String apiBase = "api.coze.cn";  // 默认值

    // JWT OAuth 相关配置
    private JwtOauth jwtOauth = new JwtOauth();

    // 内部类：封装 JWT OAuth 子配置
    @Data
    public static class JwtOauth {
        private String clientId;  // 客户端ID（必须配置）
        private String publicKeyId;  // 公钥ID（必须配置）
        private String privateKey;  // 私钥内容（直接配置）
        private Resource privateKeyFilePath;  // 私钥文件路径（资源路径，如 classpath:xxx）

        // 获取私钥内容（优先使用直接配置的privateKey，否则从文件读取）
        public String getPrivateKeyContent() {
            // 1. 优先使用直接配置的私钥
            if (privateKey != null && !privateKey.trim().isEmpty()) {
                return privateKey.trim();
            }

            // 2. 从文件读取私钥
            Assert.notNull(privateKeyFilePath, "coze.jwt-oauth.private-key 和 private-key-file-path 必须配置一个");
            try {
                return StreamUtils.copyToString(
                        privateKeyFilePath.getInputStream(),
                        StandardCharsets.UTF_8
                ).trim();
            } catch (IOException e) {
                throw new RuntimeException("私钥文件读取失败：" + privateKeyFilePath, e);
            }
        }
    }

    // 配置校验（项目启动时执行，确保必要配置已设置）
    public void validate() {
        Assert.hasText(apiBase, "coze.api-base 配置不能为空");
        Assert.notNull(jwtOauth, "coze.jwt-oauth 配置不能为空");
        Assert.hasText(jwtOauth.getClientId(), "coze.jwt-oauth.client-id 必须配置");
        Assert.hasText(jwtOauth.getPublicKeyId(), "coze.jwt-oauth.public-key-id 必须配置");
        // 校验私钥（触发 getPrivateKeyContent()，确保能正常获取）
        Assert.hasText(jwtOauth.getPrivateKeyContent(), "coze.jwt-oauth.private-key 无效");
    }

}