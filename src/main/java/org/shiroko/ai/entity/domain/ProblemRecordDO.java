package org.shiroko.ai.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ai_problem_record")
public class ProblemRecordDO {

    // 主键（指定生成策略：AUTO=数据库自增）
    @TableId(type = IdType.AUTO)
    private Long id;

    // 问题记录内容
    private String problemRecordContent;

    // 用户ID
    private Long userId;

    // 机器人回复
    private String AiResponse;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
