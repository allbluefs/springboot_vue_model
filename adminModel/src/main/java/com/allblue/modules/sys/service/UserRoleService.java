package com.allblue.modules.sys.service;

import com.allblue.modules.sys.entity.UserRoleEntity;
import com.allblue.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 */
public interface UserRoleService extends IService<UserRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据用户id获取对应的角色的id列表
     * @param userId
     * @return
     */
    List<Long> queryRoleIdList(Long userId);

    UserRoleEntity selectByUserId(Long userId);
}

