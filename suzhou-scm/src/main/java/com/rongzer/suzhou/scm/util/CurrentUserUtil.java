package com.rongzer.suzhou.scm.util;

import com.rongzer.suzhou.scm.pojo.User;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/11
 * Description:
 */
public class CurrentUserUtil {


    /**
     * 从ThreadLoacl中获取当前用户
     * @return
     */
    public static User getCurrentUser() {

        User user = UserThreadLocalUtil.get();

        return user;
    }

}