package org.shiroko.ai.entity.dto.getProblemRecordReqDTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class GetProblemRecordReqDTO {

    private Long problemRecordId;

    private Long userId;

    private String AiResponse;

    private String ProblemRecordContent;

    private LocalDateTime[] timeGap;

    @NotNull(message = "pageNum不能为空")
    private Integer pageNum;

    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

}
