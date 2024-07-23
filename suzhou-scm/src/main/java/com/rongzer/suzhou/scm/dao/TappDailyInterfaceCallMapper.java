package com.rongzer.suzhou.scm.dao;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.suzhou.scm.pojo.param.ForEnterprisesParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TappDailyInterfaceCallMapper {
    Long selectTappDailyInterfaceCallAll();
    Long selectTappDailyInterfaceCallTotal(@Param("callDate") String callDate);
    JSONObject selectTappDailyInterfaceCallDptMax(@Param("callDate") String callDate);
    JSONObject selectTappDailyInterfaceCallMax(@Param("callDate") String callDate);
    JSONObject selectTappDailyInterfaceCallMin(@Param("callDate") String callDate);
    List<JSONObject> selectApplicationScenarioTappDailyInterface(@Param("callDate") String callDate);
    List<JSONObject> selectAllTappDailyInterfaceCall();
    List<JSONObject> selectDepartmentTappDailyInterface();
}
