package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/12
 * Description:原材料入库实体bean
 */
@Getter
@Setter
public class Stock extends BaseTableData {

    /**
     * 资源入库编号
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
     * 物流编号
     */
    private String trackingNumber;

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
    @Min(0)
    @NotNull(message = "purchaseQuantity不能为空")
    private Integer purchaseQuantity;

    /**
     * 库存量
     */
    @Min(0)
    @NotNull(message = "inventory不能为空")
    private Integer inventory;

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

}