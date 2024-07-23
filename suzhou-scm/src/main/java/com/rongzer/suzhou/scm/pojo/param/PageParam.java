package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/28 10:51
 * @Version 1.0
 **/
@Getter
@Setter
public class PageParam {

    /**
     * 当前页码
     */
    @Min(1)
    @NotNull(message = "currentPage不能为空")
    private int currentPage;
    /**
     * 每页条数
     */
    @Min(1)
    @NotNull(message = "pageSize不能为空")
    private int pageSize;


}
