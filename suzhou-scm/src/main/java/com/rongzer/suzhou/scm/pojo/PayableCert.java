package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDecimal;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/27
 * Description: 应付凭证实体bean
 */
@Setter
@Getter
public class PayableCert extends BaseTableData{
    /**
     * 未签收
     */
    public static final int NOT_SIGN=0;

    /**
     * 已签收
     */
    public static final int HAS_SIGN = 1;
    /**
     * 已取消
     */
    public static final int CANCEL_SIGN=2;

    /**
     * 应付凭证编号
     */
    @JsonProperty("MAIN_ID")
    private String mainId;

    /**
     * 公司编号
     */
    private String companyId;

    /**
     * 供应商编号
     */
    private String supplierId;

    /**
     * 采购合同编号
     */
    @NotBlank(message = "contractNo不能为空")
    private String contractNo;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 原材料采购大类
     */
    @NotBlank(message = "productType1不能为空")
    private String productType1;

    /**
     * 原材料采购小类
     */
    @NotBlank(message = "productType2不能为空")
    private String productType2;

    /**
     * 采购量
     */
    @NotNull(message = "purchaseQuantity不能为空")
    private Integer purchaseQuantity;


    /**
     * 流水号
     */
    @NotBlank(message = "serialNumber不能为空")
    private String serialNumber;

    /**
     * 收款人全称
     */
    private String payeeFullName;

    /**
     * 收款人账号
     */
    private String payeeAccount;

    /**
     * 收款人开户行
     */
    private String payeeAccountBank;

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
     * 币别
     */
    @NotBlank(message = "currency不能为空")
    private String currency;

    /**
     * 金额
     */
    @IsDecimal(message = "amount最多保留两位小数")
    @NotNull(message = "amount不能为空")
    private BigDecimal amount;

    /**
     * 凭证种类
     */
    @NotBlank(message = "documentCategory不能为空")
    private String documentCategory;

    /**
     * 凭证号码
     */
    @NotBlank(message = "documentNumber不能为空")
    private String documentNumber;

    /**
     * 结算方式
     */
    @NotBlank(message = "settlementMethod不能为空")
    private String settlementMethod;

    /**
     * 用途
     */
    private String purpose;

    /**
     * 付款状态
     *  1：已付款
        2：未付款
        3：已作废

     */
    @NotNull(message = "paymentStatus不能为空")
    @Max(value = 3, message = "paymentStatus只能为1、2、3")
    @Min(value = 1,message = "paymentStatus只能为1、2、3")
    private Integer paymentStatus;

    /**
     * 签收状态  0：未签收  1：已签收
     */
    @NotNull(message = "signStatus不能为空")
    @Max(value = 1, message = "signStatus只能为0、1")
    @Min(value = 0,message = "signStatus只能为0、1")
    private Integer signStatus;

    /**
     * 有效证明文件路径
     */
    private String validFilePath;

    /**
     * pdf路径
     */
    private String pdfPath;

    /**
     * 备注
     */
    private String remark;

}