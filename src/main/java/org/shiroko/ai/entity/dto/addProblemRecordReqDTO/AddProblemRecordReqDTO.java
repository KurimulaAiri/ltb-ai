package org.shiroko.ai.entity.dto.addProblemRecordReqDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AddProblemRecordReqDTO {

    @NotEmpty(message = "records不能为空")
    @JsonProperty("records")
    @Valid
    private List<ProblemRecordDTO> records;

}
