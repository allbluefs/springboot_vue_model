package com.allblue.modules.sys.service;

import com.allblue.modules.sys.entity.RoleMenuEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 角色与菜单对应关系
 */
public interface RoleMenuService extends IService<RoleMenuEntity> {

    void deleteBatch(List<Long> roleIds);

    List<Long> queryMenuIdList(Long roleId);
}

