package com.allblue.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.allblue.modules.sys.entity.UserEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ContextHolderUtils {

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;

    }

    public static HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }
    public static UserEntity getUser(){
       return (UserEntity) getSession().getAttribute("user");
    }

}
