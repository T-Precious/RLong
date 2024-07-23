package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDate;
import com.rongzer.suzhou.scm.validation.IsDecimal;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/5/10
 * Description:销售出库和开票实体类
 */
@Setter
@Getter
public class EXWarehouseAndBill extends BaseTableData {

    /**
     * 出库单号CKD
     */
    @JsonProperty("MAIN_ID")
    private String mainId;


    /**
     * 子公司编号
     */
    private String companyId;

    /**
     * 子公司名称
     */
    private String companyName;

    /**
     * 经销商编号
     */
    private String purchaserId;

    /**
     * 客户名称（经销商名称）
     */
    private String customerName;

    /**
     * 开票单号KPD
     */
    @JsonProperty("KPD_No")
    private String kpdNo;

    /**
     * 出库日期
     */
    @IsDate(message = "deliverDate格式不正确")
    @NotBlank(message = "deliverDate不能为空")
    private String deliverDate;

    /**
     * 开票日期（算出日龄）
     */
    @IsDate(message = "billDate格式不正确")
    private String billDate;

    /**
     * 已开票 0：未开票 1：已开票
     */
    @NotNull(message = "billed不能为空")
    private Integer billed;

    /**
     * 销售合同编号
     */
    @NotBlank(message = "contractNo不能为空")
    private String contractNo;

    /**
     * 销售合同名称
     */
    private String contractName;

    /**
     * 出库仓库
     */
    @NotBlank(message = "warehouse不能为空")
    private String warehouse;

    /**
     * 仓库管理人员
     */
    @NotBlank(message = "deliver不能为空")
    private String deliver;

    /**
     * 审批人员
     */
    @NotBlank(message = "deliverApprover不能为空")
    private String deliverApprover;

    /**
     * 已审批  0：未审批   1：已审批
     */
    @NotNull(message = "approved不能为空")
    private Integer approved;

    /**
     * 已退货  0：未退货   1：已退货
     */
    @NotNull(message = "rejected不能为空")
    private Integer rejected;

    /**
     * 送货客户地址
     */
    @NotBlank(message = "customerAddress不能为空")
    private String customerAddress;

    /**
     * 运输方式（选择）
     */
    @NotBlank(message = "deliverMode不能为空")
    private String deliverMode;

    /**
     * 销售类型（选择）
     */
    @NotBlank(message = "saleMode不能为空")
    private String saleMode;

    /**
     * 生产批次（用于成本统计）
     */
    @NotBlank(message = "batchNo不能为空")
    private String batchNo;

    /**
     * 汇总净销售额整数
     */
    @IsDecimal(message = "amount最多保留两位小数")
    @NotNull(message = "amount不能为空")
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 销售出库明细
     */
    List<EXWarehouseDetail> details;

}