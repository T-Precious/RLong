package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/11/29 17:18
 * @Version 1.0
 **/
@Getter
@Setter
public class TappDailyInterfaceParam {
    /**
     * 需要获取连续的天数
     */
    @Min(0)
    @NotNull(message = "days不能为空")
    private int days;
    /**
     * true为获取后几天, false:为获取前几天
     */
    @NotNull(message = "flag不能为空")
    private Boolean flag;
    /**
     * day 标识日，month标识月，year标识年，默认为日
     */
    private String identification;
    /**
     * 查询类型（all,total,max,min）
     */
    private String type;
    /**
     * 调用日期 yyyy-MM-dd
     */
    private String callDate;

    @Override
    public String toString() {
        return "TappDailyInterfaceParam{" +
                "days=" + days +
                ", flag=" + flag +
                ", identification=" + identification +
                ", type='" + type + '\'' +
                ", callDate='" + callDate + '\'' +
                '}';
    }
}
