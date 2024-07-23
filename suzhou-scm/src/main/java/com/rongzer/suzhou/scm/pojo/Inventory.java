package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/5/30 16:48
 * @Version 1.0
 **/
@Getter
@Setter
public class Inventory extends BaseTableData {
    /**
     * 存栏记录标识
     *
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
     * 存栏大类
     */
    @NotBlank(message = "stockType1不能为空")
    private String stockType1;
    /**
     * 存栏小类
     */
    @NotBlank(message = "stockType2不能为空")
    private String stockType2;
    /**
     * 存栏量
     */
    @Min(0)
    @NotNull(message = "inventory不能为空")
    private Integer inventory;
    /**
     * 进货量/拟采购量
     */
    @Min(0)
    @NotNull(message = "purchaseVolume不能为空")
    private Integer purchaseVolume;
    /**
     * 批次
     */
    private String batch;
    /**
     * 新增变更日期
     */
    private String SYNC_DATE;
    /**
     * 新增变更时间
     */
    private String updateTime;
    /**
     * 注释
     */
    private String remark;
}
