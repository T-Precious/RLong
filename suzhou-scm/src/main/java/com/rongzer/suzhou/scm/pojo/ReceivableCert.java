package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDate;
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
 * Date: 2022/1/26
 * Description:
 */
@Setter
@Getter
public class ReceivableCert extends BaseTableData {

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
     * 应收凭证编号
     */
    @JsonProperty("MAIN_ID")
    private String mainId;

    /**
     *	公司编号
     */
    private String companyId;

    /**
     * 经销商编号
     */
    private String purchaserId;

    /**
     *	提供方
     */
    @NotBlank(message = "provider不能为空")
    private String provider;

    /**
     *	销售合同编号
     */
    @NotBlank(message = "contractNo不能为空")
    private String contractNo;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     *	货品名称
     */
    @NotBlank(message = "productName不能为空")
    private String productName;

    /**
     *	货品规格
     */
    @NotBlank(message = "productSpecifications不能为空")
    private String productSpecifications;

    /**
     *	货品数量
     */
    @NotNull(message = "productQuantity不能为空")
    private Integer productQuantity;

    /**
     *	货品重量（司斤）
     */
    @IsDecimal(message = "productWeight最多保留两位小数")
    @NotNull(message = "productWeight不能为空")
    private BigDecimal productWeight;

    /**
     *	货品单价
     */
    @IsDecimal(message = "productUnitPrice最多保留两位小数")
    @NotNull(message = "productUnitPrice不能为空")
    private BigDecimal productUnitPrice;

    /**
     *	货品总值（澳门币）
     */
    @IsDecimal(message = "productTotalValueMacao最多保留两位小数")
    @NotNull(message = "productTotalValueMacao不能为空")
    private BigDecimal productTotalValueMacao;

    /**
     *	货品总值（香港币）
     */
    private String productTotalValueHongkong;

    /**
     *	代支费用（澳门币）
     */
    @NotBlank(message = "surrogateExpensesMacao不能为空")
    private String surrogateExpensesMacao;

    /**
     *	代支费用（香港币）
     */
    private String surrogateExpensesHongkong;

    /**
     *	实收总值（澳门币）
     */
    @NotBlank(message = "grossValueMacao不能为空")
    private String grossValueMacao;

    /**
     *	实收总值（香港币）
     */
    private String grossValueHongkong;

    /**
     *	汇率（澳门）
     */
    @NotBlank(message = "exchangeRateMacao不能为空")
    private String exchangeRateMacao;

    /**
     *	汇率（香港）
     */
    private String exchangeRateHongkong;

    /**
     *	结汇额度
     */
    @NotBlank(message = "settlementQuota不能为空")
    private String settlementQuota;

    /**
     *	收款状态
     */
    @NotNull(message = "collectionStatus不能为空")
    @Max(value = 3, message = "collectionStatus只能为1、2、3")
    @Min(value = 1,message = "collectionStatus只能为1、2、3")
    private Integer collectionStatus;

    /**
     * 签收状态  0：未签收  1：已签收
     */
    @NotNull(message = "signStatus不能为空")
    @Max(value = 1, message = "signStatus只能为0、1")
    @Min(value = 0,message = "signStatus只能为0、1")
    private Integer signStatus;


    /**
     *	到达日期
     */
    @NotBlank(message = "arrivalDate不能为空")
    @IsDate(message = "arrivalDate日期格式错误")
    private String arrivalDate;

    /**
     *	销售日期
     */
    @NotBlank(message = "saleDate不能为空")
    @IsDate(message = "saleDate日期格式错误")
    private String saleDate;

    /**
     *	有效证明文件路径
     */
    private String validFilePath;

    /**
     *	pdf路径
     */
    private String pdfPath;

    /**
     * 备注
     */
    private String remark;

}