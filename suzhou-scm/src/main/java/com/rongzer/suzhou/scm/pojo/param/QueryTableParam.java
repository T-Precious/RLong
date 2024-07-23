package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Description TODO
 * @Author tiancl
 * @Date 2022/10/18 14:49
 * @Version 1.0
 **/
@Getter
@Setter
public class QueryTableParam {

    @NotBlank(message = "rowId不能为空")
    private String rowId;

    @NotBlank(message = "tableName不能为空")
    private String tableName;


    @Override
    public String toString() {
        return "QueryTableParam{" +
                "rowId='" + rowId + '\'' +
                '}';
    }
}
