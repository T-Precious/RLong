package com.rongzer.suzhou.scm.pojo.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * @Description TODO
 * @Author tiancl
 * @Date 2022/10/13 16:02
 * @Version 1.0
 **/
@Getter
@Setter
public class UploadDataParam {

    @JsonProperty("ID")
    private String Id;

    private String name;

    private String sex;

    @Override
    public String toString() {
        return "UploadDataParam{" +
                "Id='" + Id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
