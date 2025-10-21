package org.shiroko.ai.controller;

import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("/get_access_token")
    public BaseRespVO<String> getAccessToken() {
        return authService.getAccessToken();
    }
}
