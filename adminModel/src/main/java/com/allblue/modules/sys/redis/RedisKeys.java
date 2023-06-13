package com.allblue.modules.sys.redis;

/**
 * Redis所有Keys
 */
public class RedisKeys {
    public static final String SHIRO_KICKOUT_SESSION = "shiro-kickout-session:";

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    public static String getShiroSessionKey(String key){
        return "sessionid:" + key;
    }

    public static String getRetryCashKey(String key) {
        return "password-retry-cache:" + key;
    }
}
