package org.shiroko.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.shiroko.ai.entity.domain.ProblemRecordDO;
import org.shiroko.ai.entity.dto.addProblemRecordReqDTO.FieldsDTO;

@Mapper
public interface ProblemRecordMapper extends BaseMapper<ProblemRecordDO> {

    int addProblemRecord(@Param("fields") FieldsDTO dto);

}
