package org.shiroko.ai.service.impl;

import org.shiroko.ai.entity.domain.AdminDO;
import org.shiroko.ai.entity.dto.loginAdminReqDTO.LoginAdminReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.entity.vo.loginAdminRespDTO.LoginAdminRespDTO;
import org.shiroko.ai.mapper.AdminMapper;
import org.shiroko.ai.service.AdminService;
import org.shiroko.ai.util.TimeConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public BaseRespVO<LoginAdminRespDTO> login(LoginAdminReqDTO dto) {
        AdminDO admin = adminMapper.login(dto.getAccount(), dto.getPassword());
        if (admin == null) {
            return BaseRespVO.failed(400, "账号或密码错误");
        }
        LoginAdminRespDTO respDTO = new LoginAdminRespDTO();
        respDTO.setId(admin.getId());
        respDTO.setNickname(admin.getAdminNickname());
        respDTO.setUpdateTimeStr(TimeConvertUtils.localDateTimeToString(admin.getUpdateTime()));
        return BaseRespVO.succeed("登录成功", respDTO);
    }
}
