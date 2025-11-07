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

import javax.validation.Valid;
import java.security.KeyPair;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final KeyPair keyPair;

    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService, RSAKeyReader rsaKeyReader) throws Exception {
        this.adminService = adminService;
        this.keyPair = rsaKeyReader.getKeyPair();
    }

    @RequestMapping("/login")
    public BaseRespVO<LoginAdminRespDTO> login(@Valid @RequestBody LoginAdminReqDTO dto) {
        return adminService.login(dto);
    }

    @RequestMapping("/getPublicKey")
    public BaseRespVO<String> getPublicKey() {
        return BaseRespVO.succeed("public_key", keyPair.getPublic().toString());
    }

}
