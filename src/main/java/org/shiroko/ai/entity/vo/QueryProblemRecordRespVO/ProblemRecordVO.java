package org.shiroko.ai.entity.vo.QueryProblemRecordRespVO;

import lombok.Data;

@Data
public class ProblemRecordVO {

    // 主键
    private Long id;

    // 问题记录内容
    private String problemRecordContent;

    // 用户ID
    private Long userId;

    // 机器人回复
    private String AiResponse;

    // 创建时间
    private String createTimeStr;

    // 更新时间
    private String updateTimeStr;


}
