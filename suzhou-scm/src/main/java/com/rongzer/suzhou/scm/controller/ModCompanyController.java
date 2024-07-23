package com.rongzer.suzhou.scm.controller;

import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.suzhou.scm.service.ModCompanyService;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/15 14:40
 * @Version 1.0
 **/
@RestController
public class ModCompanyController {

    @Autowired
    private ModCompanyService modCompanyService;

    /**
     * @Description:查询企业信用分比列
     * @Author: tian.changlong
     * @Date: 2022/12/15 14:42
     * @return: java.lang.String
     **/
    @PostMapping("/selectModCompanyByScoreLevel")
    public String selectModCompanyByScoreLevel() {
        ReturnResult returnResult = modCompanyService.selectModCompanyByScoreLevel();
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
}
