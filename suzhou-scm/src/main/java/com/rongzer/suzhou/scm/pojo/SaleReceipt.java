package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/5/11
 * Description:销售收款单实体类
 */
@Setter
@Getter
public class SaleReceipt extends BaseTableData {

    /**
     * 收款单号SKD
     */
    @JsonProperty("MAIN_ID")
    private String mainId;

    /**
     * 子公司编号
     */
    private String companyId;

    /**
     * 子公司名称
     */
    private String companyName;

    /**
     * 流水号
     */
    private String seqNum;

    /**
     * 经销商编号
     */
    @NotBlank(message = "purchaserId不能为空")
    private String purchaserId;

    /**
     * 客户名称（经销商名称）
     */
    @NotBlank(message = "customerName不能为空")
    private String customerName;

    /**
     * 日期
     */
    @IsDate(message = "billDate格式不正确")
    @NotBlank(message = "billDate不能为空")
    private String billDate;

    /**
     * 审核人员
     */
    @NotBlank(message = "approver不能为空")
    private String approver;

    /**
     * 付款摘要信息
     */
    @NotBlank(message = "billNote不能为空")
    private String billNote;

    /**
     * 单据类别
     */
    @NotBlank(message = "billType不能为空")
    private String billType;

    /**
     * 收款方式
     */
    @NotBlank(message = "receiveMode不能为空")
    private String receiveMode;

    /**
     * 收款金额
     */
    @NotBlank(message = "receivableAmount不能为空")
    private String receivableAmount;

    /**
     * 银行账户
     */
    @NotBlank(message = "account不能为空")
    private String account;

    /**
     * 单据状态
     */
    private Integer billState;

    /**
     * 已回款金额
     */
    @NotBlank(message = "receivedAmount不能为空")
    private String receivedAmount;

    /**
     * 收款日期
     */
    @NotBlank(message = "receivedDate不能为空")
    private String receivedDate;

    /**
     * 收款登记人员
     */
    @NotBlank(message = "receiver不能为空")
    private String receiver;

    /**
     * 备注
     */
    private String remark;

    /**
     * 录入人员名称
     */
    private String inputUserName;


}