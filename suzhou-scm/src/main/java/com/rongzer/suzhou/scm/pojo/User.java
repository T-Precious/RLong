package com.rongzer.suzhou.scm.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.util.StringUtil;
import com.rongzer.suzhou.scm.constant.Consts;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2021/12/27
 * Description:
 */
@Getter
@Setter
@TableName(value = "user")
public class User {

    //token失效时间（分钟）
    public static final int TOKEN_EXPIRE_MIN = 60 * 24;

    /**
     * 手机号
     */
    @JsonProperty("MAIN_ID")
    @TableId(value = "MAIN_ID")
    @NotBlank(message = "手机号不能为空")
    private String mainId;

    /**
     * 会员编号
     */
    @JsonProperty(value = "CUSTOMER_NO")
    private String customerNo = Consts.CUSTOMER_NO;

    /**
     * 用户姓名
     */
    @NotBlank(message = "用户姓名不能为空")
    private String name;

    /**
     * 关联微信号
     */
    private String openId;

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
    @NotBlank(message = "所属公司编号不能为空")
    private String companyId;

    /**
     * 所属公司名称
     */
    private String companyName;

    /**
     * 小程序登录会话密钥
     */
    private String sessionKey;

    /**
     * 前后端交互会话token
     */
    private String token;

    /**
     * 最近一次登录时间
     */
    private String lastLoginTime;

    /**
     * 用户当前角色
     */
    @JsonIgnore
    @TableField(exist = false)
    private String currentRole;

    /**
     * 判断是否是其中一个角色
     *
     * @param currentRole
     * @return
     */
    public boolean isOneOfRole(String currentRole) {
        if (StringUtil.isEmpty(currentRole)) {
            return false;
        }

        String[] roles = this.memberRoles.split(",");
        for (int i = 0; i < roles.length; i++) {
            if (currentRole.equals(roles[i])) {
                return true;
            }
        }

        return false;
    }
}