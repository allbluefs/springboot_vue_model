package com.allblue.modules.sys.intercepter;

import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.modules.sys.service.UserService;
import com.allblue.utils.RRException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new RRException("未获取到token, 请重新登录",401);
        }
        Integer userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
        UserEntity user = userService.selectById(userId);
        if (user == null) {
            throw new RRException("token不合法",401);
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new RRException("token不合法",401);
        }
        return true;
    }
}
