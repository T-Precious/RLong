package com.rongzer.suzhou.scm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/12
 * Description: 物流实体bean
 */
@Setter
@Getter
public class Logistics extends BaseTableData{

    //已到货
    public static final int ARRIVED = 1;
    //已发货
    public static final int SHIPPED = 2;
    //已退货
    public static final int RETURNED = 3;

    //合同类型：销售
    public static final String CONTRACT_TYPE_SALE = "Sale";
    //合同类型：销售
    public static final String CONTRACT_TYPE_PURCHASE = "Purchase";

    /**
     * 物流编号
     */
    @NotBlank(message = "MAIN_ID不能为空")
    @JsonProperty("MAIN_ID")
    private String mainId;

    /**
     * 公司编号
     */
    private String companyId;

    /**
     * 关联合同类型：采购：Purchase   销售：Sale
     */
    @Pattern(regexp = "(^Purchase$|^Sale$)", message = "contractType枚举值：Purchase、Sale")
    @NotBlank(message = "contractType不能为空")
    private String contractType;

    /**
     * 关联合同编号
     */
    @NotBlank(message = "contractNo不能为空")
    private String contractNo;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 货物种类1
     */
    @NotBlank(message = "goodsType1不能为空")
    private String goodsType1;

    /**
     * 货物种类2
     */
    @NotBlank(message = "goodsType2不能为空")
    private String goodsType2;

    /**
     * 货物数量
     */
    @NotBlank(message = "goodsQuantity不能为空")
    private String goodsQuantity;

    /**
     * 寄件人
     */
    private String sender;

    /**
     * 寄件人电话
     */
    @NotBlank(message = "senderPhone不能为空")
    private String senderPhone;

    /**
     * 寄出时间
     */
    @IsDate(message = "sendTime时间格式错误")
    @NotBlank(message = "sendTime不能为空")
    private String sendTime;

    /**
     * 收货人
     */
    @NotBlank(message = "receiver不能为空")
    private String receiver;

    /**
     * 收货人电话
     */
    @NotBlank(message = "receiverPhone不能为空")
    private String receiverPhone;

    /**
     * 启运地点
     */
    @NotBlank(message = "departureLocation不能为空")
    private String departureLocation;

    /**
     * 到达地点
     */
    @NotBlank(message = "arrived不能为空")
    private String arrived;

    /**
     * 物流状态
     */
    @NotNull(message = "logisticsStatus不能为空")
    @Max(value = 3, message = "logisticsStatus只能为1、2、3")
    @Min(value = 1,message = "logisticsStatus只能为1、2、3")
    private Integer logisticsStatus;

    /**
     * 交通工具名称
     */
    private String vehicleName;

    /**
     * 交通工具识别码
     */
    private String vehicleIdentifier;

    /**
     * 驾驶员电话
     */
    private String driverPhone;

    /**
     * 关联文件路径
     */
    private String validFilePath;

}