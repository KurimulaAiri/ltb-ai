package org.shiroko.ai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.shiroko.ai.entity.dto.addProblemRecordReqDTO.FieldsDTO;

@Mapper
public interface ProblemRecordMapper {

    int addProblemRecord(@Param("fields") FieldsDTO dto);

}
