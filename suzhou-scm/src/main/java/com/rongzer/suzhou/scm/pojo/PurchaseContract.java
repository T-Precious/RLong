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
 * Date: 2022/1/4
 * Description: 采购合同bean
 */
@Setter
@Getter
public class PurchaseContract extends BaseTableData{

    //草稿状态
    public static final int CONTRACT_STATUS_DRAFT = 0;
    //进行中
    public static final int CONTRACT_STATUS_RUN = 1;
    //已完成
    public static final int CONTRACT_STATUS_DONE = 2;

    //采购部已签约-百位标识例如100
    public static final int CONTRACT_STATUS_PURCHASEDEPT_SIGN = 100;

    //供应商已签约-百位标识例如10
    public static final int CONTRACT_STATUS_SUPPLIER_SIGN = 10;

    //有关联物流记录
    public static final int HAS_LOGISTICS = 1;

    //没有关联物流记录
    public static final int NOT_HAS_LOGISTICS = 1;

    //有关联应付凭证记录
    public static final int HAS_PAYABLE_CERT = 1;

    //没有关联应付凭证记录
    public static final int NOT_HAS_PAYABLE_CERT = 0;


    /**
     * 合同编号
     */
    @NotBlank(message = "MAIN_ID不能为空")
    @JsonProperty("MAIN_ID")
    private String mainId;

    /**
     * 合同名称
     */
    @NotBlank(message = "contractName不能为空")
    private String contractName;

    /**
     * 销售合同编号
     */
    private String sellContractNo;

    /**
     * 销售合同名称
     */
    private String sellContractName;

    /**
     * 采购部公司编号
     */
    private String partyACompanyId;

    /**
     * 供应商编号
     */
    @NotBlank(message = "supplierId不能为空")
    private String supplierId;

    /**
     * 合同签订地
     */
    private String contractSignAddress;

    /**
     * 签订日期
     */
    @IsDate(message = "signDate格式不正确")
    @NotBlank(message = "signDate不能为空")
    private String signDate;

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
     * 购销数量
     */
    @NotNull(message = "purchaseSaleQuantity不能为空")
    private Integer purchaseSaleQuantity;

    /**
     * 品质规格
     */
    private String qualitySpecification;

    /**
     * 交货时间
     */
    @NotBlank(message = "deliveryTime不能为空")
    private String deliveryTime;

    /**
     * 交货地点
     */
    @NotBlank(message = "deliveryLocations不能为空")
    private String deliveryLocations;

    /**
     * 商品单价（单位为公斤）
     */
    @IsDecimal(message = "commodityPrice最多保留两位小数")
    @NotNull(message = "commodityPrice不能为空")
    private BigDecimal commodityPrice;

    /**
     * 合同状态
     */
    @Max(value = 0)
    @Min(value = 0)
    private Integer contractStatus = CONTRACT_STATUS_DRAFT;

    /**
     * 采购部名称
     */
    @NotBlank(message = "partyAName不能为空")
    private String partyAName;

    /**
     * 采购部统一社会信用代码
     */
    private String partyASocialCreditCode;

    /**
     * 采购部注册地址
     */
    private String partyARegisteredAddress;

    /**
     * 采购部注册电话
     */
    private String partyARegisterPhone;

    /**
     * 采购部开户银行
     */
    private String partyABankAccount;

    /**
     * 采购部开户账户
     */
    private String partyAAccount;

    /**
     * 采购部付款人全称
     */
    private String partyAFullName;

    /**
     * 采购部法定代表人或授权代表人姓名
     */
    private String partyALegalAuthorized;

    /**
     * 采购部联系人姓名
     */
    private String partyAcontactPerson;

    /**
     * 采购部联系电话
     */
    private String partyAcontactNumber;

    /**
     * 采购部签字人
     */
    private String partyASignatory;

    /**
     * 供应商名称
     */
    @NotBlank(message = "partyBName不能为空")
    private String partyBName;

    /**
     * 供应商统一社会信用代码
     */
    private String partyBSocialCreditCode;

    /**
     * 供应商注册地址
     */
    private String partyBRegisteredAddress;

    /**
     * 供应商注册电话
     */
    private String partyBRegisterPhone;

    /**
     * 供应商法定代表人或授权代表人姓名
     */
    private String partyBLegalAuthorized;

    /**
     * 供应商联系人姓名
     */
    private String partyBcontactPerson;

    /**
     * 供应商联系电话
     */
    private String partyBcontactNumber;

    /**
     * 供应商签字人
     */
    private String partyBSignatory;

    /**
     * 供应商开户账户
     */
    private String partyBAccount;

    /**
     * 供应商开户银行
     */
    private String partyBBankAccount;

    /**
     * 供应商付款人全称
     */
    private String partyBFullName;

    /**
     * 有效证明文件路径
     */
    private String validFilePath;

    /**
     * 签订日期-月份
     */
    private String signMonth;

    /**
     * 签订日期-年
     */
    private String signYear;

    /**
     * pdf路径
     */
    private String pdfPath;

    /**
     * 备注
     */
    private String remark;



    /**
     * 是否有关联物流记录 1：是 0：否
     */
    private int hasLogistics = NOT_HAS_LOGISTICS;

    /**
     * 最新物流信息
     */
    private String lastLogistics;

    /**
     * 是否有关联应付记录 1：是 0：否
     */
    private int hasPayableCert = NOT_HAS_PAYABLE_CERT;

}