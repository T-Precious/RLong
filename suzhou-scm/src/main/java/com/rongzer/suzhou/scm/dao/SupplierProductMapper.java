package com.rongzer.suzhou.scm.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User:
 * Date:
 * Description:数据库表读写dao
 */
@Repository
public interface SupplierProductMapper {

    /**
     * 根据子公司id查询供应商
     *
     * @param subCompanyId
     * @return
     */
    List<JSONObject> selectSupplierList(@Param("subCompanyId") String subCompanyId);

    List<JSONObject> selectClassifyList(@Param("subCompanyId") String subCompanyId, @Param("supplierName") String supplierName);

    List<JSONObject> selectProductList(@Param("subCompanyId") String subCompanyId, @Param("supplierName") String supplierName, @Param("classify") String classify);

}