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
 * Date: 2022/1/11
 * Description:损耗记录实体bean
 */
@Setter
@Getter
public class Loss extends BaseTableData{

    /**
     * 损耗编号
     */
    @JsonProperty("MAIN_ID")
    private String mainId;

    /**
     * 公司编号
     */
    private String companyId;

    /**
     * 销售合同编号
     */
    private String contractNo;

    /**
     * 销售合同名称
     */
    private String contractName;

    /**
     * 损耗类型
     */
    @NotBlank(message = "lossType1不能为空")
    private String lossType1;

    /**
     * 损耗类别
     */
    @NotBlank(message = "lossType2不能为空")
    private String lossType2;

    /**
     * 生产数量
     */
    @NotNull(message = "productionQuantity不能为空")
    @Min(0)
    private Integer productionQuantity;

    /**
     * 合格数量
     */
    @NotNull(message = "qualifiedQuantity不能为空")
    @Min(0)
    private Integer qualifiedQuantity;

    /**
     * 损耗/不合格数量
     */
    @NotNull(message = "unqualifiedQuantity不能为空")
    @Min(0)
    private Integer unqualifiedQuantity;

    /**
     * 损耗原因
     */
    @NotBlank(message = "lossCause不能为空")
    private String lossCause;

    /**
     * 损耗率
     */
    @IsDecimal(message = "attritionRate最多保留两位小数")
    @DecimalMin(value = "0.00", message = "attritionRate格式不正确")
    @DecimalMax(value = "100.00", message = "attritionRate格式不正确")
    @NotNull(message = "attritionRate不能为空")
    private BigDecimal attritionRate;

    /**
     * 损耗开始时间
     */
    @IsDate(message = "startDate格式不正确")
    private String startDate;

    /**
     * 损耗结束时间
     */
    @IsDate(message = "endDate格式不正确")
    private String endDate;

    /**
     * 周期单位
     */
    @Pattern(regexp = "^DAY$|^WEEK$|^MONTH$", message = "cycleUnit枚举值：DAY、WEEK、MONTH")
    private String cycleUnit;

    /**
     * 批次
     */
    private String batch;

}