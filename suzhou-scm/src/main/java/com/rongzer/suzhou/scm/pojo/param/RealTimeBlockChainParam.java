package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/11/29 16:11
 * @Version 1.0
 **/
@Getter
@Setter
public class RealTimeBlockChainParam {
    /**
     * 上链类型(1:企业  2:个人 3:全部 4:信用综合评价 5:信用报告 6:信用审查 7:信用承诺 8:信用修复 9:异议申诉 10:行政许可 11:行政处罚)
     */
    @NotNull(message = "upchainType不能为空")
    private Integer upchainType;
    /**
     * 创建时间 分钟 yyyy-MM-dd HH:mm
     */
    private String createDate;

    @Override
    public String toString() {
        return "RealTimeBlockChainParam{" +
                "upchainType=" + upchainType +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
