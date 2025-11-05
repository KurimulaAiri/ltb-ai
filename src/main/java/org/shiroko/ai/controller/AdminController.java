package org.shiroko.ai.controller;

import org.shiroko.ai.entity.dto.loginAdminReqDTO.LoginAdminReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.entity.vo.loginAdminRespDTO.LoginAdminRespDTO;
import org.shiroko.ai.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping("/login")
    public BaseRespVO<LoginAdminRespDTO> login(@Valid @RequestBody LoginAdminReqDTO dto) {
        return adminService.login(dto);
    }

}
