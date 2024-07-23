package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/12
 * Description: 原材料消耗记录实体bean
 */
@Getter
@Setter
public class Consume extends BaseTableData {

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
     * 采购合同编号
     */
    @NotBlank(message = "contractNo不能为空")
    private String contractNo;

    /**
     * 采购合同名称
     */
    private String contractName;

    /**
     * 资源入库编号
     */
    private String resourceStorageNumber;

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
     * 库存量
     */
    @Min(0)
    @NotNull(message = "inventory不能为空")
    private Integer inventory;

    /**
     * 消耗量
     */
    @Min(0)
    @NotNull(message = "consumption不能为空")
    private Integer consumption;

    /**
     * 供应商编号
     */
    @NotBlank(message = "supplierId不能为空")
    private String supplierId;

    /**
     * 供应商名称
     */
    @NotBlank(message = "supplierName不能为空")
    private String supplierName;

    /**
     * 开始时间
     */
    @IsDate(message = "startDate格式不正确")
    private String startDate;

    /**
     * 结束时间
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