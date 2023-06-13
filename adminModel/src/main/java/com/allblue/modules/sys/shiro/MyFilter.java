package com.allblue.modules.sys.shiro;

import cn.hutool.core.util.StrUtil;
import com.allblue.modules.sys.entity.UserEntity;
import com.allblue.modules.sys.service.UserService;
import com.allblue.utils.JacksonJsonUtil;
import com.allblue.utils.R;
import com.allblue.utils.SpringContextUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author fs
 * @Date 2023/6/12 15:05
 */

@Slf4j
public class MyFilter extends UserFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("onAccessDenied");
        String token = getRequestToken(WebUtils.toHttp(request));
        R r;
        response.setContentType("application/json; charset=utf-8");//返回json
        if (StrUtil.isNotBlank(token)) {
            UserService userService = (UserService) SpringContextUtils.getBean("userService");
            int userId;
            try {
                userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
            } catch (JWTDecodeException e) {
                r = R.error(401, "token不合法");
                response.getWriter().write(JacksonJsonUtil.obj2String(r));
                return false;
            }
            UserEntity user = userService.selectById(userId);
            if (user == null) {
                r = R.error(401, "token不合法");
                response.getWriter().write(JacksonJsonUtil.obj2String(r));
                return false;
            }
            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            try {
                jwtVerifier.verify(token);
            } catch (TokenExpiredException e) {
                r = R.error(401, "token已过期,请重新登录");
                response.getWriter().write(JacksonJsonUtil.obj2String(r));
                return false;
            } catch (Exception e) {
                r = R.error(401, "token不合法");
                response.getWriter().write(JacksonJsonUtil.obj2String(r));
                return false;
            }
        } else {
            r = R.error(401, "未获取到token, 请重新登录");
            response.getWriter().write(JacksonJsonUtil.obj2String(r));
            return false;
        }
        return true;
    }

    /**
     * 获取请求中的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpRequest.getParameter("token");
        }
        return token;
    }
}
