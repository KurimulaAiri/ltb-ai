package org.shiroko.ai.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ai_problem_record")
public class ProblemRecordDO {

    // 主键（指定生成策略：AUTO=数据库自增）
    @TableId(type = IdType.AUTO)
    private Long id;

}
