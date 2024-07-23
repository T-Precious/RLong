package com.rongzer.suzhou.scm.dao;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.suzhou.scm.pojo.param.ForEnterprisesParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ForEnterprisesMapper {
    List<JSONObject> selectAllForEnterprises();
    List<JSONObject> selectForEnterprisesBySocialCreditCode(@Param("socialCreditCode") String socialCreditCode);
    int updateForEnterprises(ForEnterprisesParam forEnterprisesParam);
}
