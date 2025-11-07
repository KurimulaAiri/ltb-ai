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
import javax.validation.Valid;
import java.security.KeyPair;
import java.security.PrivateKey;
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

        String encryptedPwd = dto.getPassword();
        // 用私钥解密
        PrivateKey privateKey = keyPair.getPrivate();
        try {
            Cipher cipher = Cipher.getInstance("RSA/OAEP");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
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
