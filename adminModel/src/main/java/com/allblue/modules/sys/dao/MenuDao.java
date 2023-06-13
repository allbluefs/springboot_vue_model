package com.allblue.modules.sys.dao;

import com.allblue.modules.sys.entity.MenuEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

public interface MenuDao extends BaseMapper<MenuEntity> {
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<MenuEntity> queryListParentId(Long parentId);
}
