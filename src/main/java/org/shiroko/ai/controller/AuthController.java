package org.shiroko.ai.controller;

import org.shiroko.ai.entity.dto.GetSessionTokenReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /***
     * 获取会话token
     * @param dto 会话token请求参数
     * @return 会话token
     */
    @RequestMapping("/get_access_token")
    public BaseRespVO<Object> getAccessToken(@Valid @RequestBody GetSessionTokenReqDTO dto) {
        return authService.getAccessToken(dto.getSessionName());
    }

}
