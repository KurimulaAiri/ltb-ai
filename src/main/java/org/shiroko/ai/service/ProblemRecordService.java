package org.shiroko.ai.service;

import org.shiroko.ai.entity.dto.addProblemRecordReqDTO.AddProblemRecordReqDTO;
import org.shiroko.ai.entity.dto.getProblemRecordReqDTO.GetProblemRecordReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.entity.vo.queryProblemRecordRespVO.QueryProblemRecordRespVO;

public interface ProblemRecordService {

    BaseRespVO<Object> addProblemRecord(AddProblemRecordReqDTO dto);

    BaseRespVO<QueryProblemRecordRespVO> getProblemRecord(GetProblemRecordReqDTO dto);
}
