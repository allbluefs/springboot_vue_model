package com.allblue.modules.sys.dao;

import com.allblue.modules.sys.entity.UserRoleEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户与角色对应关系
 */
public interface UserRoleDao extends BaseMapper<UserRoleEntity> {
	List<Long> queryRoleIdList(Long userId);

    UserRoleEntity selectByUserId(@Param("userId") Long userId);
}
