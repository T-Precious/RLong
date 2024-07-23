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
 * Description:产出记录实体bean
 */
@Getter
@Setter
public class Output extends BaseTableData{

    /**
     * 产出编号
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
     * 产出类型
     */
    @NotBlank(message = "outputType1不能为空")
    private String outputType1;

    /**
     * 产出类别
     */
    @NotBlank(message = "outputType2不能为空")
    private String outputType2;

    /**
     * 存栏量
     */
    @Min(0)
    @NotNull(message = "inventory不能为空")
    private Integer inventory;

    /**
     * 出栏量
     */
    @Min(0)
    @NotNull(message = "columnVolume不能为空")
    private Integer columnVolume;

    /**
     * 出栏占比
     */
    @IsDecimal(message = "survivalRate最多保留两位小数")
    @DecimalMin(value = "0.00", message = "survivalRate格式不正确")
    @DecimalMax(value = "100.00", message = "survivalRate格式不正确")
    @NotNull(message = "survivalRate不能为空")
    private BigDecimal survivalRate;

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