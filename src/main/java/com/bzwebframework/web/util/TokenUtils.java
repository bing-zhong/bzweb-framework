package com.bzwebframework.web.util;


import com.bzwebframework.web.constant.UserConstant;
import com.bzwebframework.web.model.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author CaptainBing
 * @Date 2023/9/21 21:39
 * @Description
 */
public class TokenUtils {

    /**
     * 获取request对象
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前用户
     * @return
     */
    public static User getCurrentUser(){
        HttpSession httpSession = getRequest().getSession();
        User user = (User) httpSession.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user != null){
            return user;
        }
        return null;
    }



    /**
     * 获取用户ID
     * @return
     */
    public static String getUserId(){
        HttpSession httpSession = getRequest().getSession();
        User user = (User) httpSession.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user != null){
            return user.getId();
        }
        return null;
    }

    /**
     * 获取当前用户登录用户名
     * @return
     */
    public static String getUserName(){
        HttpSession httpSession = getRequest().getSession();
        User user = (User) httpSession.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user != null){
            return user.getUserName();
        }
        return null;
    }


    /**
     * 获取当前用户登录账号（邮箱）
     * @return
     */
    public static String getUserAccount(){
        HttpSession httpSession = getRequest().getSession();
        User user = (User) httpSession.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user != null){
            return user.getUserAccount();
        }
        return null;
    }
}
