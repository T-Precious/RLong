package com.rongzer.suzhou.scm.controller;

import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.suzhou.scm.pojo.param.ForEnterprisesPageParam;
import com.rongzer.suzhou.scm.pojo.param.ForEnterprisesParam;
import com.rongzer.suzhou.scm.service.ForEnterprisesService;
import com.rongzer.suzhou.scm.vo.ReturnPageResult;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/28 14:01
 * @Version 1.0
 **/
@RestController
public class ForEnterprisesController {

    @Autowired
    private ForEnterprisesService forEnterprisesService;
   /**
    * @Description:查询四上企业信息
    * @Author: tian.changlong
    * @Date: 2022/12/28 14:03
    * @return: java.lang.String
    **/
    @PostMapping("/selectAllForEnterprises")
    public String selectAllForEnterprises(@Valid @RequestBody ForEnterprisesPageParam forEnterprisesPageParam){
        ReturnPageResult returnResult=forEnterprisesService.selectAllForEnterprises(forEnterprisesPageParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:通过统一社会信用代码查询四上企业信息
     * @Author: tian.changlong
     * @Date: 2022/12/28 14:04
     * @param forEnterprisesParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectForEnterprisesBySocialCreditCode")
    public String selectForEnterprisesBySocialCreditCode(@Valid @RequestBody ForEnterprisesParam forEnterprisesParam){
        ReturnResult returnResult=forEnterprisesService.selectForEnterprisesBySocialCreditCode(forEnterprisesParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:更新四上企业经纬度
     * @Author: tian.changlong
     * @Date: 2022/12/28 14:08
     * @param forEnterprisesParam:
     * @return: java.lang.String
     **/
    @PostMapping("/updateForEnterprises")
    public String updateForEnterprises(@Valid @RequestBody ForEnterprisesParam forEnterprisesParam){
        ReturnResult returnResult=forEnterprisesService.updateForEnterprises(forEnterprisesParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
}
