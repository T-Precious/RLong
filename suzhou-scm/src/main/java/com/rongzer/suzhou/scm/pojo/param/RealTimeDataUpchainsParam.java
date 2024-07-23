package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/2 14:03
 * @Version 1.0
 **/
@Getter
@Setter
public class RealTimeDataUpchainsParam {
    /**
     * 需要获取连续的分钟
     */
    @Min(0)
    @NotNull(message = "minutes不能为空")
    private int minutes;
    /**
     * true为获取后几分钟, false:为获取前几分钟
     */
    @NotNull(message = "flag不能为空")
    private Boolean flag;
    /**
     * 指定时间 yyyy-MM-dd HH:mm
     */
    private String createTime;


    /**
     * 实时上链数据统计  以前是按分钟  现在是按小时
     */
    private int hours;

    @Override
    public String toString() {
        return "RealTimeDataUpchainsParam{" +
                "minutes=" + minutes +
                ", flag=" + flag +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
