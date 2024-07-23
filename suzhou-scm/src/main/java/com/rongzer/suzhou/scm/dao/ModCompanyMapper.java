package com.rongzer.suzhou.scm.dao;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModCompanyMapper {
   List<JSONObject> selectModCompanyByScoreLevel();
   Long selectModCompanyAllNum();
}
