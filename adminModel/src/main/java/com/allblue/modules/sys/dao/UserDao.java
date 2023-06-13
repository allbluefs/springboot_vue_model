package com.allblue.modules.sys.dao;

import com.allblue.modules.sys.entity.MenuEntity;
import com.allblue.modules.sys.entity.UserEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

public interface UserDao extends BaseMapper<UserEntity> {
    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    List<MenuEntity> queryAllPerms(Long userId);
}
