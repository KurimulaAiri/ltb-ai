package org.shiroko.ai.service;

import org.shiroko.ai.entity.vo.BaseRespVO;

public interface AuthService {

    BaseRespVO<String> getAccessToken();

}
