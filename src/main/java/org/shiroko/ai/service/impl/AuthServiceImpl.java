package org.shiroko.ai.service.impl;

import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.service.AuthService;
import org.shiroko.ai.util.CozeJWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    CozeJWTUtil cozeJWTUtil;

    @Autowired
    public AuthServiceImpl(CozeJWTUtil cozeJWTUtil) {
        this.cozeJWTUtil = cozeJWTUtil;
    }

    @Override
    public BaseRespVO<String> getAccessToken() {
        try {
            return BaseRespVO.succeed("获取 access_token 成功", cozeJWTUtil.getAccessToken().getAccessToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
