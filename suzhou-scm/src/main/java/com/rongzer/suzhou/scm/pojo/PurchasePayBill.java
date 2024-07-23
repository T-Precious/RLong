package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDate;
import com.rongzer.suzhou.scm.validation.IsDecimal;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/5/10
 * Description:采购付款单实体类
 */
@Setter
@Getter
public class PurchasePayBill extends BaseTableData {

    /**

     * 采购付款单据编号CFK
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
     * 采购结算单据编号CJS
     */
    @JsonProperty("CJS_No")
    private String cjsNo;

    /**
     * 单据日期
     */
    @IsDate(message = "billDate格式不正确")
    @NotBlank(message = "billDate不能为空")
    private String billDate;

    /**
     * 流水号
     */
    private String seqNum;

    /**
     * 单据类型（支付货款等）
     */
    @NotNull(message = "billType不能为空")
    private Integer billType;

    /**
     * 收款方/供应商编号
     */
    @NotBlank(message = "supplierId不能为空")
    private String supplierId;

    /**
     * 供应商名称
     */
    @NotBlank(message = "supplierName不能为空")
    private String supplierName;

    /**
     * 收款银行
     */
    @NotBlank(message = "bank不能为空")
    private String bank;

    /**
     * 收款账户
     */
    @NotBlank(message = "account不能为空")
    private String account;

    /**
     * 收款账户开户行
     */
    @NotBlank(message = "bankBranch不能为空")
    private String bankBranch;

    /**
     * 付款金额
     */
    @IsDecimal(message = "amount最多保留两位小数")
    @NotNull(message = "amount不能为空")
    private BigDecimal amount;

    /**
     * 付款方式
     */
    private String payMode;

    /**
     * 是否作废
     */
    private Integer billState;

    /**
     * 审批人员
     */
    private String approver;

    /**
     * 审批日期
     */
    private String approvedDate;
    /**
     * 注释
     */
    private String remark;

    /**
     * 录入人员名称
     */
    private String inputUserName;
}