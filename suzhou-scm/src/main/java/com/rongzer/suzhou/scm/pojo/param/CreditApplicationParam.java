package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2023/2/8 10:44
 * @Version 1.0
 **/
@Getter
@Setter
public class CreditApplicationParam {
    /**
     * 标识，day 标识日，month标识月，year标识年，默认为日
     */
    private String identification;
    /**
     * 指定时间 yyyy-MM-dd、yyyy-MM、yyyy
     */
    private String createDate;
}
