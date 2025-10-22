package org.shiroko.ai.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetSessionTokenReqDTO {

        /**
         * 会话名称
         */
        @NotBlank(message = "sessionName不能为空")
        @JsonProperty("session_name")
        private String sessionName;

}
