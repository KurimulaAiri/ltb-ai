package org.shiroko.ai.service.impl;

import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.service.AuthService;
import org.shiroko.ai.util.CozeJWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    CozeJWTUtil cozeJWTUtil;

    @Autowired
    public AuthServiceImpl(CozeJWTUtil cozeJWTUtil) {
        this.cozeJWTUtil = cozeJWTUtil;
    }

    @Override
    public BaseRespVO<Object> getAccessToken(String sessionName) {
        try {
            Map<String, String> res = new HashMap<>();
            res.put("access_token", cozeJWTUtil.getAccessToken(sessionName).getAccessToken());
            res.put("session_name", sessionName);
            return BaseRespVO.succeed("获取 access_token 成功", res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
