package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDate;
import com.rongzer.suzhou.scm.validation.IsDecimal;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2021/12/27
 * Description: 销售合同bean
 */
@Getter
@Setter
public class SalesContract extends BaseTableData{

    //草稿状态
    public static final int CONTRACT_STATUS_DRAFT = 0;
    //进行中
    public static final int CONTRACT_STATUS_RUN = 1;
    //已完成
    public static final int CONTRACT_STATUS_DONE = 2;

    //销售部已签约-百位标识例如100
    public static final int CONTRACT_STATUS_SALEDEPT_SIGN = 100;

    //经销商已签约-百位标识例如10
    public static final int CONTRACT_STATUS_PURCHASE_SIGN = 10;

    //有关联物流记录
    public static final int HAS_LOGISTICS = 1;

    //没有关联物流记录
    public static final int NOT_HAS_LOGISTICS = 0;

    //有关联应收记录
    public static final int HAS_RECEIVABLE_CERT = 1;

    //没有关联应收记录
    public static final int NOT_HAS_RECEIVABLE_CERT = 0;

    /**
     * 合同编号
     */
    @NotBlank(message = "合同编号不能为空")
    @JsonProperty("MAIN_ID")
    private String mainId;

    /**
     * 合同名称
     */
    @NotBlank(message = "合同名称不能为空")
    private String contractName;

    /**
     * 销售部公司编号
     */
    @NotBlank(message = "partyBCompanyId不能为空")
    private String partyBCompanyId;

    /**
     * 经销商编号
     */
    @NotBlank(message = "经销商编号不能为空")
    private String purchaserId;

    /**
     * 签订日期
     */
    @IsDate(message = "签订日期格式不正确")
    @NotBlank(message = "签订日期不能为空")
    private String signDate;

    /**
     * 签订地点
     */
    @NotBlank(message = "签订地点不能为空")
    private String signPlace;

    /**
     * 合同起始日期
     */
    @IsDate(message = "合同起始日期格式不正确")
    @NotBlank(message = "合同起始日期不能为空")
    private String contractStartDate;

    /**
     * 合同终止日期
     */
    @IsDate(message = "合同终止日期格式不正确")
    @NotBlank(message = "合同终止日期不能为空")
    private String contractEndDate;

    /**
     * 币别
     */
    @NotBlank(message = "币别不能为空")
    private String currency;

    /**
     * 品名
     */
    @NotBlank(message = "品名不能为空")
    private String productType;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空")
    private String unit;

    /**
     * 包装类型
     */
    @NotBlank(message = "包装类型不能为空")
    private String packageType;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    /**
     * 参考单价
     */
    @IsDecimal(message = "referenceUnitPrice最多保留两位小数")
    @NotNull(message = "参考单价不能为空")
    private BigDecimal referenceUnitPrice;

    /**
     * 参考货值
     */
    @NotNull(message = "参考货值不能为空")
    private Integer referenceValue;

    /**
     * 交货日期
     */
    @IsDate(message = "交货日期格式不正确")
    @NotBlank(message = "交货日期不能为空")
    private String deliveryDate;

    /**
     * 总值-大写
     */
    @NotBlank(message = "大写不能为空")
    private String totalValue;

    /**
     * 合同状态
     * 草稿- 0
     * 外部经销商已签约-十位标识例如10
     * 销售部已签约-百位标识例如100
     * 进行中- 1
     * 已完成- 2
     * 已取消- 3
     */
    @Max(value = 0, message = "新增合同状态为0")
    @Min(value = 0, message = "新增合同状态为0")
    private Integer contractStatus = CONTRACT_STATUS_DRAFT;

    /**
     * 经销商返销售部手续费的百分比
     */
    @IsDecimal(message = "handlingFeePercentage最多保留两位小数")
    @DecimalMin(value = "0.00", message = "handlingFeePercentage格式不正确")
    @DecimalMax(value = "100.00", message = "handlingFeePercentage格式不正确")
    @NotNull(message = "handlingFeePercentage不能为空")
    private BigDecimal handlingFeePercentage;

    /**
     * 经销商名称
     */
    @NotBlank(message = "经销商名称不能为空")
    private String partyAName;

    /**
     * 经销商地址
     */
    private String partyAAddress;

    /**
     * 经销商联系电话
     */
    private String partyAcontactNumber;

    /**
     * 经销商签字人
     */
    private String partyASignatory;

    /**
     * 销售部名称
     */
    @NotBlank(message = "销售部名称不能为空")
    private String partyBName;

    /**
     * 销售部地址
     */
    private String partyBAddress;

    /**
     * 销售部联系电话
     */
    private String partyBcontactNumber;

    /**
     * 销售部签字人
     */
    private String partyBSignatory;

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
     * 经销商手写签名路径
     */
    private String partyASignUrl;

    /**
     * 销售部手写签名路径
     */
    private String partyBSignUrl;

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
     * 是否有关联应收记录 1：是 0：否
     */
    private int hasReceivableCert = NOT_HAS_RECEIVABLE_CERT;

    /**
     * 是否有关联采购合同 1：是 0：否
     */
    private int hasPurchaseContract = 0;


}