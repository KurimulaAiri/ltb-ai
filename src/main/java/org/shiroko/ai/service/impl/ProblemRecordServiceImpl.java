package org.shiroko.ai.service.impl;

import org.shiroko.ai.entity.dto.addProblemRecordReqDTO.AddProblemRecordReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.mapper.ProblemRecordMapper;
import org.shiroko.ai.service.ProblemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemRecordServiceImpl implements ProblemRecordService {

    private final ProblemRecordMapper problemRecordMapper;

    @Autowired
    public ProblemRecordServiceImpl(ProblemRecordMapper problemRecordMapper) {
        this.problemRecordMapper = problemRecordMapper;
    }

    @Override
    public BaseRespVO<Object> addProblemRecord(AddProblemRecordReqDTO dto) {
        int res = this.problemRecordMapper.addProblemRecord(dto.getRecords().get(0).getFields());
        if (res == 0) {
            return BaseRespVO.failed(400, "添加失败");
        } else {
            return BaseRespVO.succeed("添加成功");
        }
    }
}
