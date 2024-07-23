package com.rongzer.suzhou.scm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.constant.Consts;
import com.rongzer.suzhou.scm.pojo.param.*;
import com.rongzer.suzhou.scm.properties.CommonProperties;
import com.rongzer.suzhou.scm.service.SIPCreditService;
import com.rongzer.suzhou.scm.util.DataAnatomyUtil;
import com.rongzer.suzhou.scm.util.DateUtil;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import com.rongzer.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author tiancl
 * @Date 2022/10/13 15:46
 * @Version 1.0
 **/
@Slf4j
@Service
public class SIPCreditServiceImpl implements SIPCreditService {

    @Autowired
    private CommonProperties config;

    @Override
    public ApiResponse getBlockHeight() {
        return Consts.client.getBlockHeight(config);
    }

    @Override
    public ReturnResult getNodes() {
        int peerSize=Consts.client.getNodes(config);
        ReturnResult returnResult=new ReturnResult();
        if(peerSize>0){
            returnResult.setStatus(1);
            returnResult.setData(peerSize);
        }else{
            returnResult.setStatus(0);
            returnResult.setMsg("获取节点数量失败");
        }
        return returnResult;
    }

    @Override
    public ApiResponse getContract() {
        return Consts.client.getContract(config);
    }

    @Override
    public ReturnResult getRowHistory(QueryTableParam queryTableParam) {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询表格"+queryTableParam.getTableName()+"的rowId为"+queryTableParam.getRowId()+"的历史数据成功");
        ApiResponse apiResponse=Consts.client.getRowHistory(queryTableParam.getRowId(),queryTableParam.getTableName(),config);
        if(apiResponse==null){
            returnResult.setStatus(0);
            returnResult.setMsg("查询表格"+queryTableParam.getTableName()+"的rowId为"+queryTableParam.getRowId()+"的历史数据为空");
            log.error("查询表格"+queryTableParam.getTableName()+"的rowId为"+queryTableParam.getRowId()+"的历史数据为空");
        }else if("200".equals(apiResponse.getResultCode())){
            JSONObject jsonObject = (JSONObject) apiResponse.getData();
            JSONArray jsonArray=new JSONArray();
            JSONArray arrayList = jsonObject.getJSONArray("results");
            for (int i=0;i<arrayList.size();i++){
                JSONObject object = arrayList.getJSONObject(i);
                JSONObject valueObj = object.getJSONObject("Value");
                JSONObject rowObj=new JSONObject();
                if(valueObj.size()>0){
                    rowObj = valueObj.getJSONObject("rows");
                }
                rowObj.put("IsDelete",object.getString("IsDelete"));
                String timestamp=object.getString("Timestamp");
                timestamp=timestamp.substring(timestamp.indexOf(":")+1,timestamp.indexOf("nanos")-1);
                timestamp=DateUtil.getTimestampDate(Long.parseLong(timestamp+"000"),"yyyy-MM-dd HH:mm:ss");
                rowObj.put("Timestamp",timestamp);
                jsonArray.add(rowObj);
            }
            returnResult.setStatus(1);
            returnResult.setData(jsonArray);
        }else{
            returnResult.setStatus(0);
            returnResult.setMsg("查询表格"+queryTableParam.getTableName()+"的rowId为"+queryTableParam.getRowId()+"的历史数据异常："+apiResponse.getResultMsg());
            log.error("查询表格"+queryTableParam.getTableName()+"的rowId为"+queryTableParam.getRowId()+"的历史数据异常："+apiResponse.getResultMsg());
        }
        return returnResult;
    }

    @Override
    public ApiResponse getBlockInfo() {
        return Consts.client.getBlockInfo(config);
    }

    @Override
    public ApiResponse getBlockInfoList(QueryBlockParam queryBlockParam) {
        ApiResponse apiResponse=getBlockInfo();
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonAnatomy=DataAnatomyUtil.blockDataReturnAnatomy(apiResponse.getData());
        jsonAnatomy.put("diffTime", DateUtil.differenceTime(jsonAnatomy.getString("time").substring(0,jsonAnatomy.getString("time").lastIndexOf('.'))));
        jsonArray.add(jsonAnatomy);
        for (int i=0;i<queryBlockParam.getBlockNum()-1;i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            ApiResponse response=Consts.client.getBlockInfoByBlockHash(config,jsonObject.getString("previousBlockHash"));
            JSONObject object=DataAnatomyUtil.blockDataReturnAnatomy(response.getData());
            object.put("diffTime", DateUtil.differenceTime(object.getString("time").substring(0,object.getString("time").lastIndexOf('.'))));
            jsonArray.add(object);
        }
        apiResponse.setData(jsonArray);
        return apiResponse;
    }

    @Override
    public ApiResponse addCreditReportApply(CreditReportAppplyParam creditReportAppplyParam) {
        ApiResponse apiResponse=null;
        try {
            creditReportAppplyParam.setCreateTime(LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_1));
            String rowId="CRA"+ LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_2)+ RandomStringUtils.randomAlphanumeric(3);
            JSONObject tableData=JSONObject.parseObject(new ObjectMapper().writeValueAsString(creditReportAppplyParam));
            tableData.put("ID",rowId);
            apiResponse=Consts.client.uploadRow(rowId,Consts.T_CREDIT_REPORT_APPLY,tableData,config,true);
        } catch (Exception e) {
            log.error("新增信用报告申请异常",e);
        }
        return apiResponse;
    }

    @Override
    public ReturnResult updateCreditReportApply(CreditReportAppplyParam creditReportAppplyParam) {
        ReturnResult returnResult=new ReturnResult();
        try {
            if(StringUtils.isEmpty(creditReportAppplyParam.getId())){
                returnResult.setStatus(0);
                returnResult.setMsg("更新数据的主键不能为空");
            }else {
                ApiResponse apiResponse = Consts.client.selectDataByPrimaryKey(Consts.T_CREDIT_REPORT_APPLY, creditReportAppplyParam.getId(), config);
                if (apiResponse == null) {
                    returnResult.setStatus(0);
                    returnResult.setMsg("未查询到需要更新的数据");
                } else if ("200".equals(apiResponse.getResultCode())) {
                    JSONObject tableData = JSONObject.parseObject(new ObjectMapper().writeValueAsString(creditReportAppplyParam));
                    ApiResponse response = Consts.client.uploadRow(creditReportAppplyParam.getId(), Consts.T_CREDIT_REPORT_APPLY, tableData, config, false);
                    if ("200".equals(response.getResultCode())) {
                        returnResult.setStatus(1);
                        returnResult.setMsg("数据更新成功");
                    } else {
                        returnResult.setStatus(0);
                        returnResult.setMsg("数据更新失败");
                    }
                } else {
                    returnResult.setStatus(0);
                    returnResult.setMsg("查询需要更新的数据异常");
                }
            }
        } catch (Exception e) {
            log.error("更新数据异常",e);
        }
        return returnResult;
    }

    @Override
    public ApiResponse selectDataByPrimaryKey(QueryTableParam queryTableParam) {
        ApiResponse apiResponse=Consts.client.selectDataByPrimaryKey(Consts.T_CREDIT_REPORT_APPLY,queryTableParam.getRowId(),config);
        apiResponse.setData(DataAnatomyUtil.dataReturnAnatomy(apiResponse.getData()));
        return apiResponse;
    }

    @Override
    public ApiResponse selectCreditReportApplyByIndex(CreditReportApplyIndexParam creditReportApplyIndexParam) {
        JSONObject indexObj=new JSONObject();
        if (StringUtils.isNotEmpty(creditReportApplyIndexParam.getCertNum())){
            indexObj.put("rows.CERT_NUM",creditReportApplyIndexParam.getCertNum());
        }
        if (StringUtils.isNotEmpty(creditReportApplyIndexParam.getReportNum())){
            indexObj.put("rows.REPORT_NUM",creditReportApplyIndexParam.getReportNum());
        }
        if (StringUtils.isNotEmpty(creditReportApplyIndexParam.getUscc())){
            indexObj.put("rows.USCC",creditReportApplyIndexParam.getUscc());
        }
        if (StringUtils.isNotEmpty(creditReportApplyIndexParam.getCreateTime())){
            indexObj.put("rows.CREATE_TIME",creditReportApplyIndexParam.getCreateTime());
        }
        //是否有下一页 true有 false无
        boolean hasNextPage = true;
        ApiResponse apiResponse=Consts.client.selectPageByIndex(Consts.T_CREDIT_REPORT_APPLY,indexObj,creditReportApplyIndexParam.getBookMark(),creditReportApplyIndexParam.getPageSize(),config);
        if("200".equals(apiResponse.getResultCode())) {
//            if(StringUtils.isNotEmpty(creditReportApplyIndexParam.getBookMark())) {
                JSONObject jsonObject = (JSONObject) apiResponse.getData();
                JSONArray arrayList = jsonObject.getJSONArray("results");
                int dataSize = arrayList.size();
                if (dataSize < creditReportApplyIndexParam.getPageSize()) {
                    hasNextPage = false;
                } else if (dataSize == creditReportApplyIndexParam.getPageSize()) {
                    String bookMark=jsonObject.getString("bookmark");
                    ApiResponse response=Consts.client.selectPageByIndex(Consts.T_CREDIT_REPORT_APPLY,indexObj,bookMark,creditReportApplyIndexParam.getPageSize(),config);
                    JSONObject object = (JSONObject) response.getData();
                    JSONArray listArray = object.getJSONArray("results");
                    if(listArray.size()>0){
                        hasNextPage = true;
                    }else{
                        hasNextPage = false;
                    }
                } else {
                    hasNextPage = true;
                }
//            }
        }else{
            log.error("根据索引查询信用报告申请异常,code:"+apiResponse.getResultCode()+",msg:"+apiResponse.getResultMsg());
            hasNextPage = false;
        }
        apiResponse.setData(DataAnatomyUtil.pageDataReturnAnatomy(apiResponse.getData(),hasNextPage));
        return apiResponse;
    }

    @Override
    public ApiResponse testUploadData(UploadDataParam uploadDataParam) {
        String rowId="TT"+ LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_2)+ RandomStringUtils.randomAlphanumeric(3);
        JSONObject tableData=new JSONObject();
        tableData.put("ID",rowId);
        tableData.put("NAME",uploadDataParam.getName());
//        tableData.put("sex",uploadDataParam.getSex());
        try {
            return Consts.client.uploadRow(rowId,"T_TEST",tableData,config,true);
        }catch (Exception e){
            log.error("上链异常：e={}",e.toString());
            return null;
        }

    }
}
