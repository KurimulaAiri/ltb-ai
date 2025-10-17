package org.shiroko.ai.service;

import org.shiroko.ai.entity.dto.addProblemRecordReqDTO.AddProblemRecordReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;

public interface ProblemRecordService {

    BaseRespVO<Object> addProblemRecord(AddProblemRecordReqDTO dto);

}
