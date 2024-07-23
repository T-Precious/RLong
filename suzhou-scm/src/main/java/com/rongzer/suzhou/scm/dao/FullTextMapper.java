package com.rongzer.suzhou.scm.dao;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.suzhou.scm.pojo.SalesContract;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/3/14
 * Description:
 */
@Repository
public interface FullTextMapper {

    /**
     * 根据合同名称模糊查询
     * @param string
     * @return
     */
    List<SalesContract> fuzzySearchSalesContractByName(String string);

    /**
     *
     * @param tableName
     * @param columnName
     * @param string
     * @return
     */
    List<JSONObject> fuzzySearch(@Param("tableName") String tableName,
                                 @Param("columnName") String columnName,
                                 @Param("string") String string);
}