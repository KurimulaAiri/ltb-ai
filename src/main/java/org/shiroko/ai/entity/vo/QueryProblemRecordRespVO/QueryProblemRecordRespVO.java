package org.shiroko.ai.entity.vo.QueryProblemRecordRespVO;

import lombok.Data;

import java.util.List;

@Data
public class QueryProblemRecordRespVO {

     // 问题记录总数
     private Long total;

     // 问题记录列表
     private List<ProblemRecordVO> problemRecordVOList;

}
