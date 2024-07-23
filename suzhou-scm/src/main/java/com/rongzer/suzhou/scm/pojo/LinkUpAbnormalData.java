package com.rongzer.suzhou.scm.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description:上链异常数据实体类
 * @Author: tian.changlong
 * @Date: 2023/8/22 10:01
 * @param null:
 * @return: null
 **/
@Getter
@Setter
public class LinkUpAbnormalData {

    private String id;

    private String rowId;

    private String tableName;

    private String tableData;

    private String typeIsNew;

    private String exceptionMessage;

    private String createTime;

}