package com.rongzer.suzhou.scm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.suzhou.scm.dao.ModCompanyMapper;
import com.rongzer.suzhou.scm.service.ModCompanyService;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/15 14:33
 * @Version 1.0
 **/
@Slf4j
@Service
public class ModCompanyServiceImpl implements ModCompanyService {

    @Autowired
    private ModCompanyMapper modCompanyMapper;

    @Override
    public ReturnResult selectModCompanyByScoreLevel() {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询企业信用分比列成功");
        try {
            List<JSONObject> objectList=modCompanyMapper.selectModCompanyByScoreLevel();
            returnResult.setData(objectList);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询企业信用分比列异常："+e.getMessage());
            log.error("查询企业信用分比列异常："+e.toString());
        }
        return returnResult;
    }
}
