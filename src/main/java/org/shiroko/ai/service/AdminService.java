package org.shiroko.ai.service;

import org.shiroko.ai.entity.dto.loginAdminReqDTO.LoginAdminReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.entity.vo.loginAdminRespDTO.LoginAdminRespDTO;

public interface AdminService {

     BaseRespVO<LoginAdminRespDTO> login(LoginAdminReqDTO dto);

}
