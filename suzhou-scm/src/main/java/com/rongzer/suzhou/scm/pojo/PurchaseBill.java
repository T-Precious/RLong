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
 * Date: 2022/5/9
 * Description:采购发票实体类
 */
@Setter
@Getter
public class PurchaseBill extends BaseTableData {

    /**
     * 收票单编号SPD
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
     * 供应商编号
     */
    private String supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 采购合同编号
     */
    @NotBlank(message = "contractNo不能为空")
    private String contractNo;

    /**
     * 采购合同名称
     */
    private String contractName;

    /**
     * 含税金额
     */
    @IsDecimal(message = "amount最多保留两位小数")
    @NotNull(message = "amount不能为空")
    private BigDecimal amount;

    /**
     * 扣税金额
     */
    @IsDecimal(message = "amountNoTax最多保留两位小数")
    @NotNull(message = "amountNoTax不能为空")
    private BigDecimal amountNoTax;

    /**
     * 单据日期（算出日龄）
     */
    @IsDate(message = "billDate格式不正确")
    @NotBlank(message = "billDate不能为空")
    private String billDate;

    /**
     * 发票类型（选择）
     */
    @NotNull(message = "billType不能为空")
    private Integer billType;

    /**
     * 单据状态
     */
    @NotNull(message = "billState不能为空")
    private Integer billState;

    /**
     * 备注
     */
    private String remark;

}