package org.shiroko.ai.controller;

import org.shiroko.ai.entity.dto.loginAdminReqDTO.LoginAdminReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.entity.vo.loginAdminRespDTO.LoginAdminRespDTO;
import org.shiroko.ai.service.AdminService;
import org.shiroko.ai.util.RSAKeyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.validation.Valid;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final RSAKeyReader rsaKeyReader;

    private final KeyPair keyPair;

    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService, RSAKeyReader rsaKeyReader) throws Exception {
        this.adminService = adminService;
        this.rsaKeyReader = rsaKeyReader;
        this.keyPair = rsaKeyReader.getKeyPair();
    }

    @RequestMapping("/login")
    public BaseRespVO<LoginAdminRespDTO> login(@Valid @RequestBody LoginAdminReqDTO dto) {

        System.out.println(dto);

        String encryptedPwd = dto.getPassword();
        // 用私钥解密
        PrivateKey privateKey = keyPair.getPrivate();
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

            // 2. 显式配置 OAEP 参数（与前端 oaepOptions 完全一致）
            OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                    "SHA-256", // 哈希算法：与前端 forge.md.sha256 匹配
                    "MGF1",    // 掩码生成函数：固定为 MGF1
                    new MGF1ParameterSpec("SHA-256"), // MGF1 的哈希算法：与前端 mgf1.md 匹配
                    PSource.PSpecified.DEFAULT       // 可选参数，默认即可
            );
            cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPwd));
            String plainPassword = new String(decryptedBytes); // 解密后的明文密码
            dto.setPassword(plainPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseRespVO.failed(500,"服务器出错");
        }
        return adminService.login(dto);
    }

    @RequestMapping("/getPublicKey")
    public BaseRespVO<String> getPublicKey() {
        return BaseRespVO.succeed("public_key", rsaKeyReader.getPublicKeyContent());
    }

}
