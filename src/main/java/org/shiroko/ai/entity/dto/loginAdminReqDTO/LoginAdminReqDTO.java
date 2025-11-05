package org.shiroko.ai.entity.dto.loginAdminReqDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginAdminReqDTO {

    @NotEmpty(message = "账号不能为空")
    private String account;

    @NotEmpty(message = "密码不能为空")
    private String password;

}
