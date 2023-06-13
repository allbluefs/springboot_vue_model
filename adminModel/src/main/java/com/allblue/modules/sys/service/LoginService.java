package com.allblue.modules.sys.service;

import com.allblue.modules.sys.entity.LoginForm;
import com.allblue.modules.sys.entity.UserEntity;

/**
 * @Description 登录处理
 * @Author fs
 * @Date 2023/6/9 16:24
 */
public interface LoginService {
    /**
     * 登录
     *
     * @param loginForm 登录表单
     */
    UserEntity login(LoginForm loginForm);
}
