package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2023/2/9 15:01
 * @Version 1.0
 **/
@Getter
@Setter
public class InterfaceUsageParam {
    /**
     * 查询类型（all,total,max,min）
     */
    @NotNull(message = "type不能为空")
    private String type;
    /**
     * day 标识日，month标识月，year标识年，默认为日
     */
    private String identification;
    /**
     * 调用日期 yyyy-MM-dd/yyyy-MM/yyyy
     */
    private String callDate;

    @Override
    public String toString() {
        return "InterfaceUsageParam{" +
                "type='" + type + '\'' +
                ", identification='" + identification + '\'' +
                ", callDate='" + callDate + '\'' +
                '}';
    }
}
