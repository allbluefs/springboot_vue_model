package com.allblue.modules.sys.service;

import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.utils.PageUtils;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;


public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);
    void save(UserEntity user);
    void update(UserEntity user);

}

