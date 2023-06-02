package com.allblue.modules.sys.service.impl;

import com.allblue.modules.sys.dao.UserDao;
import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.modules.sys.service.UserService;
import com.allblue.utils.PageUtils;
import com.allblue.utils.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;


@Service("tUserService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        EntityWrapper<UserEntity> ew = new EntityWrapper<>();
        ew.like(StringUtils.isNotBlank(username), "username", username);
        ew.ne("user_id", 1);
        Page<UserEntity> page = this.selectPage(
                new Query<UserEntity>(params).getPage(), ew);
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserEntity user) {
        this.insert(user);
    }

    @Override
    public void update(UserEntity user) {
        this.updateById(user);
    }
}
