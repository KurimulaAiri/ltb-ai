package org.shiroko.ai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.shiroko.ai.entity.domain.AdminDO;

@Mapper
public interface AdminMapper {

    AdminDO login(String adminAccount, String adminPassword);

}
