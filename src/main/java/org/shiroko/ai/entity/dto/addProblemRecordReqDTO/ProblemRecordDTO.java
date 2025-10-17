package org.shiroko.ai.entity.dto.addProblemRecordReqDTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProblemRecordDTO {

    @NotNull(message = "fields不能为空")
    private FieldsDTO fields;

}
