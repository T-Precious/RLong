package com.rongzer.suzhou.scm.controller;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.pojo.param.*;
import com.rongzer.suzhou.scm.properties.CommonProperties;
import com.rongzer.suzhou.scm.service.ChannelService;
import com.rongzer.suzhou.scm.service.SIPCreditService;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description 苏州信用可视化监控大屏接口
 * @Author tiancl
 * @Date 2022/10/13 15:30
 * @Version 1.0
 **/
@Slf4j
@RestController
public class SIPCreditController {

    @Autowired
     private SIPCreditService sipCreditService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private CommonProperties config;
    /**
     * @Description:获取区块链高度
     * @Author: tian.changlong
     * @Date: 2022/10/18 14:22
     * @return: java.lang.String
     **/
    @PostMapping("/getBlockHeight")
    public String getBlockHeight(){
        ApiResponse apiResponse=sipCreditService.getBlockHeight();
        if("200".equals(apiResponse.getResultCode())){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,apiResponse).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,apiResponse).toString();
        }
    }
    /**
     * @Description:获取节点数量
     * @Author: tian.changlong
     * @Date: 2022/10/18 14:44
     * @return: java.lang.String
     **/
    @PostMapping("/getNodes")
    public String getNodes(){
        ReturnResult returnResult=sipCreditService.getNodes();
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:获取通道中已经安装的合约数量
     * @Author: tian.changlong
     * @Date: 2022/10/18 14:46
     * @return: java.lang.String
     **/
    @PostMapping("/getContract")
    public String getContract(){
        ApiResponse apiResponse=sipCreditService.getContract();
        if(apiResponse==null) {
            return new ResultVo<>(CommonConst.ERROR_STATUS,"获取通道中合约数量异常(NullPointerException)").toString();
        }else if ("200".equals(apiResponse.getResultCode())){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,apiResponse).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,apiResponse).toString();
        }
    }
    /**
     * @Description:获取信用报告申请数据历史信息
     * @Author: tian.changlong
     * @Date: 2022/10/18 14:53
     * @param queryTableParam:
     * @return: java.lang.String
     **/
    @PostMapping("/getRowHistory")
    public String getRowHistory(@Valid @RequestBody QueryTableParam queryTableParam){
        ReturnResult returnResult=sipCreditService.getRowHistory(queryTableParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询区块信息
     * @Author: tian.changlong
     * @Date: 2022/10/20 16:21
     * @return: java.lang.String
     **/
    @PostMapping("/getBlockInfo")
    public String getBlockInfo(){
        ApiResponse apiResponse=sipCreditService.getBlockInfo();
        if("200".equals(apiResponse.getResultCode())){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,apiResponse).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,apiResponse).toString();
        }
    }
    /**
     * @Description:查询区块列表信息
     * @Author: tian.changlong
     * @Date: 2022/10/21 14:30
     * @param queryBlockParam: 
     * @return: java.lang.String
     **/
    @PostMapping("/getBlockInfoList")
    public String getBlockInfoList(@Valid @RequestBody QueryBlockParam queryBlockParam){
        ApiResponse apiResponse=sipCreditService.getBlockInfoList(queryBlockParam);
        if("200".equals(apiResponse.getResultCode())){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,apiResponse).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,apiResponse).toString();
        }
    }
    /**
     * @Description:新增信用报告申请
     * @Author: tian.changlong
     * @Date: 2022/10/18 14:21
     * @param creditReportAppplyParam: 
     * @return: java.lang.String
     **/
    @PostMapping("/addCreditReportApply")
    public String addCreditReportApply(@Valid @RequestBody CreditReportAppplyParam creditReportAppplyParam){
        ApiResponse apiResponse=sipCreditService.addCreditReportApply(creditReportAppplyParam);
        if("200".equals(apiResponse.getResultCode())){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,apiResponse).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,apiResponse).toString();
        }
    }
    /**
     * @Description:更新信用报告申请数据
     * @Author: tian.changlong
     * @Date: 2022/10/19 14:41
     * @param creditReportAppplyParam:
     * @return: java.lang.String
     **/
    @PostMapping("/updateCreditReportApply")
    public String updateCreditReportApply(@Valid @RequestBody CreditReportAppplyParam creditReportAppplyParam){
        ReturnResult returnResult=sipCreditService.updateCreditReportApply(creditReportAppplyParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:根据主键查询信用报告申请
     * @Author: tian.changlong
     * @Date: 2022/10/18 14:57
     * @param queryTableParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectDataByPrimaryKey")
    public String selectDataByPrimaryKey(@Valid @RequestBody QueryTableParam queryTableParam){
        ApiResponse apiResponse=sipCreditService.selectDataByPrimaryKey(queryTableParam);
        if(apiResponse==null) {
            return new ResultVo<>(CommonConst.ERROR_STATUS,"根据主键查询信用报告申请异常(NullPointerException)").toString();
        }else if("200".equals(apiResponse.getResultCode())){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,apiResponse).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,apiResponse).toString();
        }
    }
    /**
     * @Description:根据索引查询信用报告申请
     * @Author: tian.changlong
     * @Date: 2022/10/20 14:40
     * @param creditReportApplyIndexParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectCreditReportApplyByIndex")
    public String selectCreditReportApplyByIndex(@Valid @RequestBody CreditReportApplyIndexParam creditReportApplyIndexParam){
        ApiResponse apiResponse=sipCreditService.selectCreditReportApplyByIndex(creditReportApplyIndexParam);
        if(apiResponse==null) {
            return new ResultVo<>(CommonConst.ERROR_STATUS,"根据索引查询信用报告申请异常(NullPointerException)").toString();
        }else if("200".equals(apiResponse.getResultCode())){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,apiResponse).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,apiResponse).toString();
        }
    }
    @PostMapping("/testUploadData")
    public String testUploadData(@Valid @RequestBody UploadDataParam uploadDataParam){
        ApiResponse apiResponse=sipCreditService.testUploadData(uploadDataParam);
        if("200".equals(apiResponse.getResultCode())){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,apiResponse).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,apiResponse).toString();
        }
    }

    /**
     * 获取历史交易记录
     * @param day 获取几天的交易记录
     * @return
     */
    @GetMapping("/getHistoryTransaction")
    public String getHistoryTransaction(@RequestParam int day){
        if(ObjectUtils.isEmpty(day)){
            day = 7;
        }
        String channelName = config.getChannelName();
        //根据名称查询t_channel表,获取channelId
        JSONObject channelJson = channelService.getChannelByChannelName(channelName);
        if(ObjectUtils.isEmpty(channelJson)){
            return new JSONObject().toJSONString();
        }
        String id = channelJson.getString("id");
        //统计t_rbaas_channel_trade表获取交易记录
        List<JSONObject> countJson = channelService.countHistoryTransaction(day,id);

        return new ResultVo<>(CommonConst.SUCCESS_STATUS,countJson).toString();
    }
}
