package com.rongzer.suzhou.scm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: xujun
 * Date: 2022/5/31
 * Description: 子公司bean
 */
@Getter
@Setter
@TableName(value = "sub_company")
public class SubCompany extends BaseTableData {

    /**
     * 统一社会信用代码
     */
    @NotBlank(message = "MAIN_ID不能为空")
    @JsonProperty("MAIN_ID")
    @TableId(value = "MAIN_ID")
    private String mainId;

    /**
     * 公司名称
     */
    @NotBlank(message = "name不能为空")
    private String name;

    /**
     * 公司简称
     */
    private String shortName;

    /**
     * 注册地址
     */
    private String registeredAddress;

    /**
     * 注册电话
     */
    private String registerPhone;

    /**
     * 法定代表人姓名
     */
    private String legalAuthorized;

    /**
     * 备注
     */
    private String remark;

    /**
     * 银行账户全称
     */
    private String accountFullName;

    /**
     * 银行账户
     */
    private String bankAccount;

    /**
     * 开户行
     */
    private String accountBank;

    /**
     * 开户行编号
     */
    private String accountBankCode;

    /**
     * 角色分类集合
     */
    private String memberRoleList;

    /**
     * 开票税号
     */
    private String taxNumber;

}