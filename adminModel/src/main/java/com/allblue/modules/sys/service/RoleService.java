package com.allblue.modules.sys.service;

import com.allblue.modules.sys.entity.RoleEntity;
import com.allblue.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 角色
 */
public interface RoleService extends IService<RoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(RoleEntity role);

    void update(RoleEntity role);

    List<RoleEntity> getRoleByUserId(Long userId);

    List<RoleEntity> selectAll(Map<String, Object> params);

    void deleteBatchIds(Long[] roleIds);

    Long getRoleIdByName(String roleName);

    RoleEntity selectByUserId(Long userId);
}

