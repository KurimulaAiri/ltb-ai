package org.shiroko.ai.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GetSessionTokenReqDTO {

        /**
         * 会话名称
         */
        @NotEmpty(message = "session_name不能为空")
        @JsonProperty("session_name")
        private String sessionName;

}
