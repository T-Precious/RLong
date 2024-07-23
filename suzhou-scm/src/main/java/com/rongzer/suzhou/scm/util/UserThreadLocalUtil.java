package com.rongzer.suzhou.scm.util;

import com.rongzer.suzhou.scm.pojo.User;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/5
 * Description: 登录用户信息存放到ThreadLocal工具类
 */
public class UserThreadLocalUtil {

    public static ThreadLocal<User> threadLocal = new ThreadLocal<User>();


    public static void set(User user) {
        threadLocal.set(user);
    }

    public static User get() {
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}