package com.allblue.modules.sys.shiro;

import cn.hutool.core.date.DateUtil;
import com.allblue.modules.sys.entity.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TokenUtils {

    /**
     * 生成token
     */
    public static String genToken(UserEntity user) {
        return JWT.create().withExpiresAt(DateUtil.offsetMinute(new Date(), 1)).withAudience(user.getUserId().toString())
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    /**
     * 获取token中的用户信息
     */
    public static Long getUserId(String token) {
        try {
            String aud = JWT.decode(token).getAudience().get(0);
            return Long.valueOf(aud);
        } catch (Exception e) {
            log.error("解析token失败", e);
            return null;
        }
    }
}
