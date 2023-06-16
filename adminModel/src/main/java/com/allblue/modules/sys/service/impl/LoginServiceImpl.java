package com.allblue.modules.sys.service.impl;

import com.allblue.modules.sys.entity.LoginForm;
import com.allblue.modules.sys.entity.MenuEntity;
import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.modules.sys.service.LoginService;
import com.allblue.modules.sys.service.UserService;
import com.allblue.modules.sys.shiro.CustomToken;
import com.allblue.modules.sys.shiro.ShiroUtils;
import com.allblue.modules.sys.shiro.TokenUtils;
import com.allblue.utils.RRException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 登录实现实现类
 * @Author fs
 * @Date 2023/6/9 16:26
 */
@Service("loginService")
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserService userService;


    @Override
    public UserEntity login(LoginForm loginForm) {
        Subject subject = ShiroUtils.getSubject();
        CustomToken customToken = new CustomToken(loginForm.getUsername(), loginForm.getPassword());
        try {
            subject.login(customToken);
        } catch (IncorrectCredentialsException e) {
            throw new RRException("账号或密码不正确");
        } catch (RRException e) {
            throw new RRException(e.getMsg(),e.getCode());
        } catch (AuthenticationException e) {
            log.info("账户验证失败msg{}", e.getMessage());
            throw new RRException("账户验证失败");
        }
        UserEntity user = (UserEntity) subject.getPrincipal();
        String token = TokenUtils.genToken(user);
        user.setToken(token);
        List<MenuEntity> perms = userService.queryAllPerms(user.getUserId());
        user.setPermissions(perms);
        return user;
    }
}
