package com.rongzer.suzhou.scm.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/28 10:17
 * @Version 1.0
 **/
@Getter
@Setter
public class ForEnterprises {
    /**
     * 企业名称
     */
    private String enterpriseName;
    /**
     * 统一社会信用代码
     */
    private String socialCreditCode;
    /**
     * 功能区
     */
    private String functionalArea;
    /**
     * 注册地址
     */
    private String registeredAddress;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
}
