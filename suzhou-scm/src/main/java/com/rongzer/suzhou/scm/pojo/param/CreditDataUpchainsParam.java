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
public class CreditDataUpchainsParam {
    /**
     * 需要获取连续的天/月/年数
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
     * 标识，day 标识日，month标识月，year标识年，默认为日
     */
    private String identification;
    /**
     * 指定时间 yyyy-MM-dd、yyyy-MM、yyyy
     */
    private String createDate;

    @Override
    public String toString() {
        return "CreditDataUpchainsParam{" +
                "days=" + days +
                ", flag=" + flag +
                ", identification=" + identification +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
