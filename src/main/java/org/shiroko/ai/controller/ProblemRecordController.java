package org.shiroko.ai.controller;

import org.shiroko.ai.entity.dto.addProblemRecordReqDTO.AddProblemRecordReqDTO;
import org.shiroko.ai.entity.dto.getProblemRecordReqDTO.GetProblemRecordReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.entity.vo.QueryProblemRecordRespVO.QueryProblemRecordRespVO;
import org.shiroko.ai.service.ProblemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/problem_record")
public class ProblemRecordController {

    private final ProblemRecordService problemRecordService;

    @Autowired
    public ProblemRecordController(ProblemRecordService problemRecordService) {
        this.problemRecordService = problemRecordService;
    }

    /***
     * 添加问题记录
     * @param dto 问题记录DTO
     * @return 响应DTO
     */
    @RequestMapping("/add")
    public BaseRespVO<Object> addProblemRecord(@Valid @RequestBody AddProblemRecordReqDTO dto) {
//        System.out.println("addProblemRecord, dto: " + dto);
        return this.problemRecordService.addProblemRecord(dto);
    }

    @RequestMapping("/get")
    public BaseRespVO<QueryProblemRecordRespVO> getProblemRecord(@Valid @RequestBody GetProblemRecordReqDTO dto) {
//        System.out.println("getProblemRecord, dto: " + dto);
        return this.problemRecordService.getProblemRecord(dto);
    }

}
