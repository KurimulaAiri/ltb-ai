package org.shiroko.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shiroko.ai.entity.domain.ProblemRecordDO;
import org.shiroko.ai.entity.dto.addProblemRecordReqDTO.AddProblemRecordReqDTO;
import org.shiroko.ai.entity.dto.getProblemRecordReqDTO.GetProblemRecordReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.entity.vo.QueryProblemRecordRespVO.ProblemRecordVO;
import org.shiroko.ai.entity.vo.QueryProblemRecordRespVO.QueryProblemRecordRespVO;
import org.shiroko.ai.mapper.ProblemRecordMapper;
import org.shiroko.ai.service.ProblemRecordService;
import org.shiroko.ai.util.PageConvertUtils;
import org.shiroko.ai.util.TimeConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public BaseRespVO<QueryProblemRecordRespVO> getProblemRecord(GetProblemRecordReqDTO dto) {
        QueryWrapper<ProblemRecordDO> queryWrapper = new QueryWrapper<>();
        if (dto.getUserId() != null) {
            queryWrapper.ge("user_id", dto.getUserId());
        }
        if (dto.getProblemRecordContent() != null) {
            queryWrapper.like("problem_record_content", dto.getProblemRecordContent());
        }
        if (dto.getAiResponse() != null) {
            queryWrapper.like("ai_response", dto.getAiResponse());
        }
        if (dto.getTimeGap() != null && dto.getTimeGap().length == 2) {
            queryWrapper.between("create_time", dto.getTimeGap()[0], dto.getTimeGap()[1]);
        }
        queryWrapper.orderByDesc("id");

        Page<ProblemRecordDO> page = new Page<>(dto.getPageNum(), dto.getPageSize());

        IPage<ProblemRecordDO> problemRecordDOIPage = this.problemRecordMapper.selectPage(page, queryWrapper);
        IPage<ProblemRecordVO> problemRecordVOIPage = PageConvertUtils.convert(problemRecordDOIPage, problemRecordDO -> {
            ProblemRecordVO problemRecordVO = new ProblemRecordVO();
            problemRecordVO.setId(problemRecordDO.getId());
            problemRecordVO.setUserId(problemRecordDO.getUserId());
            problemRecordVO.setProblemRecordContent(problemRecordDO.getProblemRecordContent());
            problemRecordVO.setAiResponse(problemRecordDO.getAiResponse());
            problemRecordVO.setCreateTimeStr(TimeConvertUtils.localDateTimeToString(problemRecordDO.getCreateTime(), TimeConvertUtils.DEFAULT_DATETIME_FORMAT));
            problemRecordVO.setUpdateTimeStr(TimeConvertUtils.localDateTimeToString(problemRecordDO.getUpdateTime(), TimeConvertUtils.DEFAULT_DATETIME_FORMAT));
            return problemRecordVO;
        });

        List<ProblemRecordVO> problemRecordVOList = problemRecordVOIPage.getRecords();
        QueryProblemRecordRespVO respVO = new QueryProblemRecordRespVO();
        respVO.setTotal(problemRecordVOIPage.getTotal());
        respVO.setProblemRecordVOList(problemRecordVOList);

        return BaseRespVO.succeed(respVO);
    }
}
