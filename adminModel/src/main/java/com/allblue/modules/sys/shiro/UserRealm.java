package com.allblue.modules.sys.shiro;

import com.allblue.modules.sys.entity.MenuEntity;
import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.modules.sys.service.UserService;
import com.allblue.utils.RRException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 认证
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;


    protected final Map<Object, Object> sessionMap = new HashMap<Object, Object>();


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserEntity user = (UserEntity) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();
        List<MenuEntity> permsList = userService.queryAllPerms(userId);
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (MenuEntity menu : permsList) {
            if (StringUtils.isBlank(menu.getPerms())) {
                continue;
            }
            String perms = menu.getPerms();
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        CustomToken customToken = (CustomToken) authcToken;
        System.out.println("认证" + customToken.getUsername());
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authcToken.getPrincipal() == null) {
            throw new RRException("未登录", 401);
        }
        // 获取用户信息
        String name = authcToken.getPrincipal().toString();
        UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("username", name));
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            System.out.println(simpleAuthenticationInfo);
            return simpleAuthenticationInfo;
        }
    }

}
