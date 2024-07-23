package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/28 10:51
 * @Version 1.0
 **/
@Getter
@Setter
public class ForEnterprisesParam {
    /**
     * 统一社会信用代码
     */
    @NotBlank(message = "socialCreditCode不能为空")
    private String socialCreditCode;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;

    @Override
    public String toString() {
        return "ForEnterprisesParam{" +
                "socialCreditCode='" + socialCreditCode + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
