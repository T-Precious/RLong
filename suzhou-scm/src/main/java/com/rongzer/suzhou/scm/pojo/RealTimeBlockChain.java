package com.rongzer.suzhou.scm.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/11/29 11:24
 * @Version 1.0
 **/
@Getter
@Setter
public class RealTimeBlockChain {
    /**
     * 上链类型(1:企业  2:个人)
     */
    @NotNull(message = "upchainType不能为空")
    private Integer upchainType;
    /**
     * 上链数量
     */
    @NotNull(message = "upchainNumber不能为空")
    private Long upchainNumber;
    /**
     * 创建时间 分钟
     */
    private String createDate;
    /**
     * 创建时间 秒
     */
    private String createDateTime;
}
