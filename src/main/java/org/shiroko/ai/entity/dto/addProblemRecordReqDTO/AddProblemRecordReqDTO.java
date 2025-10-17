package org.shiroko.ai.entity.dto.addProblemRecordReqDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddProblemRecordReqDTO {

    @NotNull(message = "records不能为空")
    @JsonProperty("records")
    private List<ProblemRecordDTO> records;

}
