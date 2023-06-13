package com.allblue.modules.sys.shiro;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description 自定义令牌
 * @Author fs
 * @Date 2023/6/9 14:46
 */
@Data
public class CustomToken extends UsernamePasswordToken {


    public CustomToken() {
        super();
    }

    public CustomToken(String username, String password) {
        super(username, password);
    }
}
