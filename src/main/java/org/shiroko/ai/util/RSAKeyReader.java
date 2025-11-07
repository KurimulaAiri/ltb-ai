package org.shiroko.ai.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@Data
public class RSAKeyReader {

    // 注入资源加载器（用于读取classpath下的文件，开发环境方便）
    private final ResourceLoader resourceLoader;

    // 密钥文件路径（建议从配置文件读取，灵活适配环境）
    private final String publicKeyPath;  // 如："classpath:rsa/rsa_public.pem" 或 "/opt/keys/rsa_public.pem"
    private final String privateKeyPath; // 如："classpath:rsa/rsa_private.pem" 或 "/opt/keys/rsa_private.pem"

    private String publicKeyContent; // 公钥内容
    private String privateKeyContent; // 私钥内容

    // 构造函数：从配置文件注入路径（通过@ConfigurationProperties或@Value）
    public RSAKeyReader(ResourceLoader resourceLoader,
                        @Value("${rsa.public-key-path}") String publicKeyPath,
                        @Value("${rsa.private-key-path}") String privateKeyPath) {
        this.resourceLoader = resourceLoader;
        this.publicKeyPath = publicKeyPath;
        this.privateKeyPath = privateKeyPath;
    }

    /**
     * 读取公钥文件，解析为PublicKey对象
     */
    public PublicKey readPublicKey() throws Exception {
        // 1. 读取文件内容（支持classpath或绝对路径）
        String publicKeyPem = readKeyFile(publicKeyPath);

        // 3. 缓存公钥内容
        publicKeyContent = publicKeyPem;

        // 2. 清洗格式：去除BEGIN/END标识和换行符
        String publicKeyStr = publicKeyPem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("[\\r\\n]", "");

        // 3. Base64解码
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);



        // 4. 生成PublicKey对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
    }

    /**
     * 读取私钥文件，解析为PrivateKey对象
     */
    public PrivateKey readPrivateKey() throws Exception {
        // 1. 读取文件内容
        String privateKeyPem = readKeyFile(privateKeyPath);
        // 3. 缓存私钥内容
        privateKeyContent = privateKeyPem;

        // 2. 清洗格式：去除BEGIN/END标识和换行符
        String privateKeyStr = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("[\\r\\n]", "");

        // 3. Base64解码
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);

        // 4. 生成PrivateKey对象（注意私钥用PKCS8规范）
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
    }

    /**
     * 通用方法：读取密钥文件内容（支持classpath路径和绝对路径）
     */
    private String readKeyFile(String filePath) throws IOException {
        if (filePath.startsWith("classpath:")) {
            // 读取classpath下的文件（开发环境常用，如src/main/resources/下的文件）
            Resource resource = resourceLoader.getResource(filePath);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } else {
            // 读取绝对路径文件（生产环境常用，如服务器上的/opt/keys/目录）
            return Files.readString(Paths.get(filePath));
        }
    }

    /**
     * 生成KeyPair（公钥+私钥对）
     */
    public KeyPair getKeyPair() throws Exception {
        return new KeyPair(readPublicKey(), readPrivateKey());
    }
}