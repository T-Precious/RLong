package com.rongzer.suzhou.scm.dao;


import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SevenBusinessTypeMapper {
    JSONObject selectCreditDataUpchainsNumber(@Param("syncDate") String syncDate);
    Long selectCreditCommitmentAllNum();
    Long selectTCreditReviewApplyAllNum();
    Long selectADMLicenseAllNum();
    Long selectADMPENALTYAllNum();
    Long selectTCreditReportApplyAllNum();
    Long selectTCreditRepairApplyAllNum();
    Long selectTCreditObjectionAppealApplyAllNum();
}
