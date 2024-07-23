package com.rongzer.suzhou.scm.dao;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.suzhou.scm.pojo.param.EnterpriseParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface DepartmentChainNumMapper {
    List<Map<String,Object>> countByDateType(String createDateStr);

    int gatherAdm(String syncDate);

    int hasGarherByCreateDate();

    int deleteHistoryGather();

    List<JSONObject> getModCompanyPage(EnterpriseParam enterpriseParam);

    List<JSONObject> getReportApplyPage(EnterpriseParam enterpriseParam);

    List<JSONObject> getReviewApplyPage(EnterpriseParam enterpriseParam);

    List<JSONObject> getReviewApplyListPage(@Param("reviewId") String reviewId);

    List<JSONObject> getCommitmentPage(@Param("enterpriseParam") EnterpriseParam enterpriseParam);

    List<String> getAllDepartments();

    List<JSONObject> getRepairApplyPage(EnterpriseParam enterpriseParam);

    List<JSONObject> getObjectionAppealApplyPage(EnterpriseParam enterpriseParam);

    JSONObject getDataRecordByID(@Param("sourceRecordId") String sourceRecordId);

    List<JSONObject> getAdmLicensePage(EnterpriseParam enterpriseParam);

    List<JSONObject> getAdmPenaltyPage(EnterpriseParam enterpriseParam);
}
