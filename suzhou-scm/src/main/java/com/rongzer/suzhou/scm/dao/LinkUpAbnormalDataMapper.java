package com.rongzer.suzhou.scm.dao;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.suzhou.scm.pojo.LinkUpAbnormalData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:上链异常数据的处理
 * @Author: tian.changlong
 * @Date: 2023/8/22 9:47
 * @param null:
 * @return: null
 **/
@Repository
public interface LinkUpAbnormalDataMapper{

    List<JSONObject> selectLinkUpAbnormalDataList(@Param("tableName") String tableName);

    JSONObject getLinkUpAbnormalDataByRowId(@Param("rowId") String rowId);

    int insertLinkUpAbnormalData(LinkUpAbnormalData linkUpAbnormalData);

}