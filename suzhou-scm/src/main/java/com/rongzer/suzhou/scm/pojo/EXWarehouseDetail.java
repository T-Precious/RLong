package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDecimal;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/5/10
 * Description:销售出库明细实体类
 */
@Setter
@Getter
public class EXWarehouseDetail extends BaseTableData {

    /**
     * 出库单号CKD
     */
    @JsonProperty("MAIN_ID")
    @NotBlank(message = "MAIN_ID不能为空")
    private String mainId;

    /**
     * 子公司编号
     */
    private String companyId;

    /**
     * 产品编号
     */
    @NotBlank(message = "productNo不能为空")
    private String productNo;

    /**
     * 子公司名称
     */
    private String companyName;

    /**
     * 产品大类
     */
    @NotBlank(message = "product1不能为空")
    private String product1;

    /**
     * 产品小类
     */
    @NotBlank(message = "product2不能为空")
    private String product2;

    /**
     * 规格
     */
    @NotBlank(message = "spec不能为空")
    private String spec;

    /**
     * 单位
     */
    @NotBlank(message = "unit不能为空")
    private String unit;

    /**
     * 销售金额
     */
    @IsDecimal(message = "grossAmount最多保留两位小数")
    @NotNull(message = "grossAmount不能为空")
    private BigDecimal grossAmount;

    /**
     * 折扣金额
     */
    @IsDecimal(message = "discountAmount最多保留两位小数")
    @NotNull(message = "discountAmount不能为空")
    private BigDecimal discountAmount;

    /**
     * 净销售额
     */
    @IsDecimal(message = "amount最多保留两位小数")
    @NotNull(message = "amount不能为空")
    private BigDecimal amount;

}