package org.shiroko.ai.util;

import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.service.auth.JWTOAuth;
import com.coze.openapi.service.auth.JWTOAuthClient;
import com.coze.openapi.service.service.CozeAPI;
import org.shiroko.ai.config.CozeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Coze JWT 工具类（依赖配置文件中的常量）
 */
@Component  // 交给 Spring 管理，支持依赖注入
public class CozeJWTUtil {

    // 注入配置类
    private final CozeConfig cozeConfig;

    // 构造器注入（推荐）
    @Autowired
    public CozeJWTUtil(CozeConfig cozeConfig) {
        this.cozeConfig = cozeConfig;
    }

    // 初始化时校验配置（可选，确保配置有效）
    @PostConstruct
    public void init() {
        cozeConfig.validate();  // 触发配置校验，无效则启动失败
    }

    // 获取 access_token 示例
    public OAuthToken getAccessToken() throws Exception {
        JWTOAuthClient oauth = new JWTOAuthClient.JWTOAuthBuilder()

                .clientID(cozeConfig.getJwtOauth().getClientId())  // 客户端ID
                .privateKey(cozeConfig.getJwtOauth().getPrivateKeyContent())  // 私钥内容
                .publicKey(cozeConfig.getJwtOauth().getPublicKeyId())  // 公钥ID
                .baseURL(cozeConfig.getApiBase())  // API域名
                .build();

        return oauth.getAccessToken();
    }

    // 初始化 Coze API 客户端
    public CozeAPI getCozeApi() throws Exception {
        JWTOAuthClient oauth = new JWTOAuthClient.JWTOAuthBuilder()
                .clientID(cozeConfig.getJwtOauth().getClientId())
                .privateKey(cozeConfig.getJwtOauth().getPrivateKeyContent())
                .publicKey(cozeConfig.getJwtOauth().getPublicKeyId())
                .baseURL(cozeConfig.getApiBase())
                .build();

        return new CozeAPI.Builder()
                .auth(JWTOAuth.builder().jwtClient(oauth).build())
                .baseURL(cozeConfig.getApiBase())
                .build();
    }
}