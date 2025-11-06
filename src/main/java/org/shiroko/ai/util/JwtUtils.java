package org.shiroko.ai.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    // 从配置文件读取 JWT 密钥（建议在 application.yml 中配置，长度至少 256 位）
    @Value("${jwt.secret}")
    private String secret;

    // 从配置文件读取 Token 过期时间（单位：毫秒，如 86400000 表示 24 小时）
    @Value("${jwt.expiration}")
    private long expiration;

    // 生成签名密钥（基于配置的 secret）
    private SecretKey getSigningKey() {
        // 使用 HMAC-SHA256 算法，密钥需足够长（256 位以上）
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成 Token
     * @param account 用户名（或用户唯一标识，如 userid）
     * @return JWT 字符串
     */
    public String generateToken(String account) {
        // 载荷（Payload）：存放自定义信息（如用户角色、权限等）
        Map<String, Object> claims = new HashMap<>();
        // 示例：添加用户角色（可选）
        // claims.put("role", "ADMIN");

        return Jwts.builder()
                .setClaims(claims) // 设置自定义载荷
                .setSubject(account) // 设置主题（通常是用户名/用户ID）
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + (expiration * 1000 * 60 * 20))) // 过期时间 20 分钟
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 签名（使用密钥和算法）
                .compact(); // 生成 Token 字符串
    }

    /**
     * 验证 Token 有效性
     * @param token 待验证的 Token
     * @return 是否有效（true：有效；false：无效/过期/签名错误）
     */
    public boolean validateToken(String token) {
        try {
            // 解析 Token 并验证签名和过期时间
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 捕获所有 JWT 相关异常（过期、签名错误等）
            return false;
        }
    }

    /**
     * 从 Token 中获取用户名（主题）
     */
    public String getAccountFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 从 Token 中获取自定义载荷信息
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // 解析 Token 中的所有载荷
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}