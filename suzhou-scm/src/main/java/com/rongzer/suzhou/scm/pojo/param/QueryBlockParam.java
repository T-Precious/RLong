package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/10/21 11:28
 * @Version 1.0
 **/
@Getter
@Setter
public class QueryBlockParam {

    @NotNull(message = "blockNum不能为空")
    private Integer blockNum;

    @Override
    public String toString() {
        return "QueryBlockParam{" +
                "blockNum=" + blockNum +
                '}';
    }
}
