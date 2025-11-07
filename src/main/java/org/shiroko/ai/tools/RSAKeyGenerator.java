package org.shiroko.ai.tools;

import java.io.FileWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RSAKeyGenerator {
    public static void main(String[] args) throws Exception {
        // 1. 配置密钥参数（2048位安全足够，4096位更安全但性能略降）
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();

        // 2. 提取公钥、私钥，转为PEM格式（前端/后端可直接解析）
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()) +
                "\n-----END PUBLIC KEY-----";

        String privateKey = "-----BEGIN PRIVATE KEY-----\n" +
                Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()) +
                "\n-----END PRIVATE KEY-----";

        // 3. 写入本地文件（保存到项目根目录，后续复制到配置）
        writeFile("trans_public_key.pem", publicKey);
        writeFile("trans_private_key.pem", privateKey);

        System.out.println("密钥生成成功！已保存到项目根目录：");
        System.out.println("公钥：trans_public_key.pem");
        System.out.println("私钥：trans_private_key.pem");
    }

    // 写入文件工具方法
    private static void writeFile(String fileName, String content) throws Exception {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        }
    }
}