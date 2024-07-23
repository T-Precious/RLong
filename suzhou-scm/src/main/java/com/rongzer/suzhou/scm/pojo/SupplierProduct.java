package com.rongzer.suzhou.scm.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: xujun
 * Date: 2022/05/18
 * Description: 供应商和物料bean
 */
@Getter
@Setter
public class SupplierProduct {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 子公司编号
     */
    private String subCompanyId;

    /**
     * 子公司名称
     */
    private String subCompanyName;

    /**
     * 类型
     */
    private String classify;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商编号
     */
    private String supplierCode;

    /**
     * 供应商产品名称
     */
    private String productName;

    /**
     * 规格
     */
    private String productSpec;

    /**
     * 单位
     */
    private String productUnit;

}