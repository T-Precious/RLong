package com.rongzer.suzhou.scm.controller;

import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.suzhou.scm.pojo.param.EnterpriseParam;
import com.rongzer.suzhou.scm.pojo.param.PageParam;
import com.rongzer.suzhou.scm.service.DepartmentChainNumService;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: pshe
 * @Date: 2023/2/7 9:45
 */
@RestController
public class DepartmentChainNumController {

    @Autowired
    private DepartmentChainNumService departmentChainNumService;
    /**
     * 统计部门上链数据
     * @return
     */
    @GetMapping("/countByDateType")
    public String countByDateType(String dateType){
        ReturnResult returnResult = departmentChainNumService.countByDateType(dateType);
        if(returnResult.getStatus() == 1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }


    /**
     * 区块链数据查询与溯源
     * @param enterpriseParam
     * @return
     */
    @PostMapping("/getBusinessData")
    public String getBusinessData(@RequestBody EnterpriseParam enterpriseParam){
        String[] businesses = enterpriseParam.getBusinesses();
        PageParam pageParam = new PageParam();
        pageParam.setCurrentPage(enterpriseParam.getCurrentPage()==null?1:enterpriseParam.getCurrentPage());
        pageParam.setPageSize(enterpriseParam.getPageSize()==null?10:enterpriseParam.getPageSize());
        if(businesses == null || businesses.length == 0){
            return new ResultVo<>(CommonConst.ERROR_STATUS,"业务代码不能为空").toString();
        }
        ReturnResult returnResult = departmentChainNumService.getBusinessData(enterpriseParam, pageParam);
        if(returnResult.getStatus() == 1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }


    /**
     * 导出数据
     *
     * @return
     */
    @PostMapping("/exportBusinessData")
    public String exportBusinessData(@RequestBody EnterpriseParam enterpriseParam){
        String[] businesses = enterpriseParam.getBusinesses();
        if(businesses == null || businesses.length == 0){
            return new ResultVo<>(CommonConst.ERROR_STATUS,"业务代码不能为空").toString();
        }
        PageParam pageParam = new PageParam();
        pageParam.setCurrentPage(1);
        pageParam.setPageSize(5000);
        try{
            departmentChainNumService.exportBusinessData(enterpriseParam,pageParam);
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,"下载完成").toString();
        }catch (Exception e){
            e.printStackTrace();
            return new ResultVo<>(CommonConst.ERROR_STATUS,"下载失败"+e.getMessage()).toString();
        }
    }


}
