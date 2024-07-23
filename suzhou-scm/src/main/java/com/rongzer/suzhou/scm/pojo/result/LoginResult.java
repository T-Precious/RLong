package com.rongzer.suzhou.scm.pojo.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/7
 * Description: 登录接口返回数据bean
 */
@Setter
@Getter
public class LoginResult {

    /**
     * 手机号
     */
    private String phoneNumer;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 联盟链成员，及角色
     */
    private String memberRoles;

    /**
     * 默认角色
     */
    private String defaultRole;

    /**
     * 所属公司编号
     */
    private String companyId;

    /**
     * 前后端交互会话token
     */
    private String token;
}