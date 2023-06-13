package com.allblue.modules.sys.dao;

import com.allblue.modules.sys.entity.RoleEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色
 */
@Repository
public interface RoleDao extends BaseMapper<RoleEntity> {

    List<RoleEntity> getRoleByUserId(@Param("userId") Long userId);

    Long getRoleIdByName(@Param("roleName") String roleName);

    RoleEntity selectByUserId(@Param("userId") Long userId);
}
