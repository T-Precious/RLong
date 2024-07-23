package com.rongzer.suzhou.scm.controller;

import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.pojo.param.*;
import com.rongzer.suzhou.scm.service.ChannelService;
import com.rongzer.suzhou.scm.service.LeadCockpitService;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/11/14 10:22
 * @Version 1.0
 **/
@RestController
public class LeadCockpitController {

    @Autowired
    private LeadCockpitService leadCockpitService;
    @Autowired
    private ChannelService channelService;

    /**
     * @Description:信用报告申请数据上链趋势
     * @Author: tian.changlong
     * @Date: 2022/11/15 15:30
     * @return: java.lang.String
     **/
    @PostMapping("/selectCreditDataLinkTrend")
    public String selectCreditDataLinkTrend(){
        ApiResponse apiResponse=leadCockpitService.selectCreditDataLinkTrend();
        if("200".equals(apiResponse.getResultCode())){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,apiResponse).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,apiResponse).toString();
        }
    }
    /**
     * @Description:查询表格中行记录总数
     * @Author: tian.changlong
     * @Date: 2022/11/24 9:57
     * @return: java.lang.String
     **/
    @PostMapping("/getRowCount")
    public String getRowCount(){
        ReturnResult returnResult=leadCockpitService.getRowCount();
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询某个表格中行记录总数
     * @Author: tian.changlong
     * @Date: 2022/11/24 9:57
     * @return: java.lang.String
     **/
    @PostMapping("/getTableRowCount")
    public String getTableRowCount(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam){
        ReturnResult returnResult=leadCockpitService.getTableRowCount(syncRBCTableDataJobParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询公共信用应用统计
     * @Author: tian.changlong
     * @Date: 2022/12/2 15:59
     * @return: java.lang.String
     **/
    @PostMapping("/selectCreditApplication")
    public String selectCreditApplication(@Valid @RequestBody CreditApplicationParam creditApplicationParam){
        ReturnResult returnResult=leadCockpitService.selectCreditApplication(creditApplicationParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询信用业务上链数据总累计
     * @Author: tian.changlong
     * @Date: 2022/11/24 9:57
     * @return: java.lang.String
     **/
    @PostMapping("/selectUpchainsNumber")
    public String selectUpchainsNumber(@Valid @RequestBody RealTimeBlockChainParam realTimeBlockChainParam){
        ReturnResult returnResult=leadCockpitService.selectUpchainsNumber(realTimeBlockChainParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询七大信用业务上链数据累计
     * @Author: tian.changlong
     * @Date: 2022/11/24 9:57
     * @return: java.lang.String
     **/
    @PostMapping("/selectSevenBusinessUpchainsNumber")
    public String selectSevenBusinessUpchainsNumber(){
        ReturnResult returnResult=leadCockpitService.selectSevenBusinessUpchainsNumber();
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询信用数据上链趋势
     * @Author: tian.changlong
     * @Date: 2022/11/24 9:57
     * @return: java.lang.String
     **/
    @PostMapping("/selectCreditDataUpchainsNumber")
    public String selectCreditDataUpchainsNumber(@Valid @RequestBody CreditDataUpchainsParam creditDataUpchainsParam){
        ReturnResult returnResult=leadCockpitService.selectCreditDataUpchainsNumber(creditDataUpchainsParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询实时数据上链趋势
     * @Author: tian.changlong
     * @Date: 2022/12/2 14:20
     * @param realTimeDataUpchainsParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectRealTimeDataUpchainsNumber")
    public String selectRealTimeDataUpchainsNumber(@Valid @RequestBody RealTimeDataUpchainsParam realTimeDataUpchainsParam){
        /*ReturnResult returnResult=leadCockpitService.selectRealTimeDataUpchainsNumber(realTimeDataUpchainsParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }*/
        try{
            int hours = realTimeDataUpchainsParam.getHours();
            if(ObjectUtils.isEmpty(hours)){
                hours = 7;
            }
            Map<String,Integer> countUpChainMap = channelService.countActualTimeUpChain(hours);
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,countUpChainMap).toString();
        }catch (Exception e){
            return new ResultVo<>(CommonConst.ERROR_STATUS,"查询实时数据上链趋势异常").toString();
        }
    }
    /**
     * @Description:查询上链增长趋势
     * @Author: tian.changlong
     * @Date: 2022/12/2 14:57
     * @param creditDataUpchainsParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectOnChainGrowthTrend")
    public String selectOnChainGrowthTrend(@Valid @RequestBody CreditDataUpchainsParam creditDataUpchainsParam){
        ReturnResult returnResult=leadCockpitService.selectOnChainGrowthTrend(creditDataUpchainsParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:通过统一社会信用代码查询企业评分等级信息
     * @Author: tian.changlong
     * @Date: 2022/12/30 14:11
     * @param forEnterprisesParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectEnterpriseInfoByUscc")
    public String selectEnterpriseInfoByUscc(@Valid @RequestBody ForEnterprisesParam forEnterprisesParam){
        //查区块链
//        ReturnResult returnResult=leadCockpitService.selectEnterpriseInfoByUscc(forEnterprisesParam);
        //查数据库
        ReturnResult returnResult=leadCockpitService.selectEnterpriseMysqlByUscc(forEnterprisesParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    @PostMapping("/selectLegalNumByIndex")
    public String selectLegalNumByIndex(){
        ReturnResult returnResult=leadCockpitService.selectLegalNumByIndex();
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    @PostMapping("/selectPersonalNumByIndex")
    public String selectPersonalNumByIndex(){
        ReturnResult returnResult=leadCockpitService.selectPersonalNumByIndex();
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
}
