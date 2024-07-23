package com.rongzer.suzhou.scm.vo;

import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2021/12/29
 * Description: 区块链根据索引查询列表返回定义
 */
@Getter
@Setter
public class RBCQueryListResult {

    /**
     * 根据索引查询到的总记录数
     */
    private int rnum;

    /**
     * 当前分页返回数据列表
     */
    private JSONArray list;
}