package com.rongzer.suzhou.scm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2021/12/31
 * Description: 经销商实体bean
 */
@Getter
@Setter
@TableName(value = "purchaser")
public class Purchaser extends BaseTableData{

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
    @NotBlank(message = "shortName不能为空")
    private String shortName;

    /**
     * 注册地址
     */
    @NotBlank(message = "registeredAddress不能为空")
    private String registeredAddress;

    /**
     * 注册电话
     */
    @NotBlank(message = "registerPhone不能为空")
    private String registerPhone;

    /**
     * 法定代表人姓名
     */
    @NotBlank(message = "legalAuthorized不能为空")
    private String legalAuthorized;

    /**
     * 联系人姓名
     */
    private String contractName;

    /**
     * 联系人电话
     */
    private String contractPhone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关联子公司统一社会信用代码
     */
    private String subCompanyId;

    /**
     * 付款人全称
     */
    private String payerFullName;

    /**
     * 付款人账号
     */
    private String payerAccount;

    /**
     * 付款人开户行
     */
    private String payerAccountBank;

    /**
     * 付款人开户行编号
     */
    private String payerAccountBankCode;

    /**
     * 开票税号
     */
    private String taxNumber;

}