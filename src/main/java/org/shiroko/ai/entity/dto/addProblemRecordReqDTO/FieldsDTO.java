package org.shiroko.ai.entity.dto.addProblemRecordReqDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * fields对象DTO（对应JSON的fields节点）
 */
@Data
public class FieldsDTO {

    // 映射JSON的"Bot 回复"字段（键名含空格，必须用@JsonProperty指定）
    @NotNull(message = "bot_resp不能为空")
    @JsonProperty("bot_resp")
    private String botReply;

    // 映射JSON的"用户问题"字段（同理，指定JSON键名）
    @NotNull(message = "user_req不能为空")
    @JsonProperty("user_req")
    private String problemRecordContent;

    // 映射JSON的"用户ID"字段（同理，指定JSON键名）
    @NotNull(message = "用户ID不能为空")
    @JsonProperty("user_id")
    private Long userId;

}
