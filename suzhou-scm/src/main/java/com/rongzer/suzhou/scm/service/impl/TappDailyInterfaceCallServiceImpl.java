package com.rongzer.suzhou.scm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.suzhou.scm.dao.TappDailyInterfaceCallMapper;
import com.rongzer.suzhou.scm.pojo.param.InterfaceUsageParam;
import com.rongzer.suzhou.scm.pojo.param.TappDailyInterfaceParam;
import com.rongzer.suzhou.scm.service.TappDailyInterfaceCallService;
import com.rongzer.suzhou.scm.util.CalculateUtil;
import com.rongzer.suzhou.scm.util.DateUtil;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import com.rongzer.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2023/1/3 16:44
 * @Version 1.0
 **/
@Slf4j
@Service
public class TappDailyInterfaceCallServiceImpl implements TappDailyInterfaceCallService {

    @Autowired
    private TappDailyInterfaceCallMapper tappDailyInterfaceCallMapper;

    @Override
    public ReturnResult selectTappDailyInterfaceCallByCondition(InterfaceUsageParam interfaceUsageParam) {
        ReturnResult returnResult=new ReturnResult();
        JSONObject resultObj=new JSONObject();
        try {
            returnResult.setStatus(1);
            returnResult.setMsg("查询接口使用量统计成功");
            if("dptMax".equals(interfaceUsageParam.getType())){
                JSONObject dptMaxObj=selectTappDailyInterfaceGeneralPurpose(interfaceUsageParam,"dptMax");
                resultObj.put("dptMaxObj",dptMaxObj);
            }else if("all".equals(interfaceUsageParam.getType())){
                JSONObject allObj=selectTappDailyInterfaceGeneralPurpose(interfaceUsageParam,"all");
                resultObj.put("allObj",allObj);
            }else if("max".equals(interfaceUsageParam.getType())){
                JSONObject maxObj=selectTappDailyInterfaceGeneralPurpose(interfaceUsageParam,"max");
                resultObj.put("maxObj",maxObj);
            }else if("min".equals(interfaceUsageParam.getType())){
                JSONObject minObj=selectTappDailyInterfaceGeneralPurpose(interfaceUsageParam,"min");
                resultObj.put("minObj",minObj);
            }else if("total".equals(interfaceUsageParam.getType())){
                JSONObject totalObj=selectTappDailyInterfaceGeneralPurpose(interfaceUsageParam,"total");
                resultObj.put("totalObj",totalObj);
            }else{
                returnResult.setStatus(0);
                returnResult.setMsg("查询接口使用量统计,参数查询类型值为未知值");
            }
            returnResult.setData(resultObj);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询接口使用量统计异常："+e.getMessage());
            log.error("method:selectTappDailyInterfaceCallByCondition,param:"+interfaceUsageParam.toString()+",查询接口使用量统计异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectAllTappDailyInterfaceCall() {
        ReturnResult returnResult=new ReturnResult();
        try {
            List<JSONObject> objectList=tappDailyInterfaceCallMapper.selectAllTappDailyInterfaceCall();
            log.info("method:selectAllTappDailyInterfaceCall,查询接口使用量排行："+objectList.toString());
            returnResult.setData(objectList);
            returnResult.setStatus(1);
            returnResult.setMsg("查询接口使用量排行成功");
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询接口使用量排行异常："+e.getMessage());
            log.error("method:selectAllTappDailyInterfaceCall,查询接口使用量排行异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectDepartmentTappDailyInterface() {
        ReturnResult returnResult=new ReturnResult();
        try {
            List<JSONObject> objectList=tappDailyInterfaceCallMapper.selectDepartmentTappDailyInterface();
            log.info("method:selectDepartmentTappDailyInterface,查询各部门接口使用量："+objectList.toString());
            returnResult.setData(objectList);
            returnResult.setStatus(1);
            returnResult.setMsg("查询各部门接口使用量成功");
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询各部门接口使用量异常："+e.getMessage());
            log.error("method:selectDepartmentTappDailyInterface,查询各部门接口使用量异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectTimePeriodTappDailyInterface(TappDailyInterfaceParam tappDailyInterfaceParam) {
        ReturnResult returnResult=new ReturnResult();
        JSONObject resultObj=new JSONObject();
        try {
            if(StringUtils.isEmpty(tappDailyInterfaceParam.getIdentification())){
                tappDailyInterfaceParam.setIdentification("day");
            }
            if("day".equals(tappDailyInterfaceParam.getIdentification())){
                if(StringUtils.isEmpty(tappDailyInterfaceParam.getCallDate())){
                    tappDailyInterfaceParam.setCallDate(DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,tappDailyInterfaceParam.getFlag(),1));
                }
            }else if("month".equals(tappDailyInterfaceParam.getIdentification())){
                if(StringUtils.isEmpty(tappDailyInterfaceParam.getCallDate())){
                    tappDailyInterfaceParam.setCallDate(DateUtil.getFirstFewMonth(LocalDateUtil.nowDate("yyyy-MM"),"yyyy-MM",tappDailyInterfaceParam.getFlag(),1));
                }
            }else if("year".equals(tappDailyInterfaceParam.getIdentification())){
                if(StringUtils.isEmpty(tappDailyInterfaceParam.getCallDate())){
                    tappDailyInterfaceParam.setCallDate(DateUtil.getFirstFewYear(LocalDateUtil.nowDate("yyyy"),"yyyy",tappDailyInterfaceParam.getFlag(),1));
                }
            }
            Long interfaceTotal = tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallTotal(tappDailyInterfaceParam.getCallDate());
            if(interfaceTotal==null){
                interfaceTotal=0L;
            }
            resultObj.put(tappDailyInterfaceParam.getCallDate(),interfaceTotal);
            for(int i=1;i<tappDailyInterfaceParam.getDays();i++){
                String callDate=null;
                if("day".equals(tappDailyInterfaceParam.getIdentification())){
                    callDate=DateUtil.getFirstFewDays(tappDailyInterfaceParam.getCallDate(),LocalDateUtil.DATE_FORMAT_1,tappDailyInterfaceParam.getFlag(),i);
                }else if("month".equals(tappDailyInterfaceParam.getIdentification())){
                    callDate=DateUtil.getFirstFewMonth(tappDailyInterfaceParam.getCallDate(),"yyyy-MM",tappDailyInterfaceParam.getFlag(),i);
                }else if("year".equals(tappDailyInterfaceParam.getIdentification())){
                    callDate=DateUtil.getFirstFewYear(tappDailyInterfaceParam.getCallDate(),"yyyy",tappDailyInterfaceParam.getFlag(),i);
                }
                Long num1=tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallTotal(callDate);
                if(num1==null){
                    num1=0L;
                }
                resultObj.put(callDate,num1);
            }
            returnResult.setStatus(1);
            returnResult.setMsg("查询各时间段接口使用量成功");
            returnResult.setData(DateUtil.sortDateDesc(resultObj));
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询各时间段接口使用量异常："+e.getMessage());
            log.error("method:selectTimePeriodTappDailyInterface,param:"+tappDailyInterfaceParam.toString()+",查询各时间段接口使用量异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectApplicationScenarioTappDailyInterface(TappDailyInterfaceParam tappDailyInterfaceParam) {
        ReturnResult returnResult=new ReturnResult();
        JSONObject resultObj=new JSONObject();
        try {
            if(StringUtils.isEmpty(tappDailyInterfaceParam.getIdentification())){
                tappDailyInterfaceParam.setIdentification("day");
            }
            if("day".equals(tappDailyInterfaceParam.getIdentification())){
                if(StringUtils.isEmpty(tappDailyInterfaceParam.getCallDate())){
                    tappDailyInterfaceParam.setCallDate(DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,tappDailyInterfaceParam.getFlag(),1));
                }
            }else if("month".equals(tappDailyInterfaceParam.getIdentification())){
                if(StringUtils.isEmpty(tappDailyInterfaceParam.getCallDate())){
                    tappDailyInterfaceParam.setCallDate(DateUtil.getFirstFewMonth(LocalDateUtil.nowDate("yyyy-MM"),"yyyy-MM",tappDailyInterfaceParam.getFlag(),1));
                }
            }else if("year".equals(tappDailyInterfaceParam.getIdentification())){
                if(StringUtils.isEmpty(tappDailyInterfaceParam.getCallDate())){
                    tappDailyInterfaceParam.setCallDate(DateUtil.getFirstFewYear(LocalDateUtil.nowDate("yyyy"),"yyyy",tappDailyInterfaceParam.getFlag(),1));
                }
            }
            List<JSONObject> objectList=tappDailyInterfaceCallMapper.selectApplicationScenarioTappDailyInterface(tappDailyInterfaceParam.getCallDate());
            resultObj.put(tappDailyInterfaceParam.getCallDate(),objectList);
            for(int i=1;i<tappDailyInterfaceParam.getDays();i++){
                String callDate=null;
                if("day".equals(tappDailyInterfaceParam.getIdentification())){
                    callDate=DateUtil.getFirstFewDays(tappDailyInterfaceParam.getCallDate(),LocalDateUtil.DATE_FORMAT_1,tappDailyInterfaceParam.getFlag(),i);
                }else if("month".equals(tappDailyInterfaceParam.getIdentification())){
                    callDate=DateUtil.getFirstFewMonth(tappDailyInterfaceParam.getCallDate(),"yyyy-MM",tappDailyInterfaceParam.getFlag(),i);
                }else if("year".equals(tappDailyInterfaceParam.getIdentification())){
                    callDate=DateUtil.getFirstFewYear(tappDailyInterfaceParam.getCallDate(),"yyyy",tappDailyInterfaceParam.getFlag(),i);
                }
                List<JSONObject> list=tappDailyInterfaceCallMapper.selectApplicationScenarioTappDailyInterface(callDate);
                resultObj.put(callDate,list);
            }
            returnResult.setStatus(1);
            returnResult.setMsg("查询业务场景各时间段接口使用量成功");
            returnResult.setData(DateUtil.sortDateDesc(resultObj));
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询业务场景各时间段接口使用量异常："+e.getMessage());
            log.error("method:selectApplicationScenarioTappDailyInterface,param:"+tappDailyInterfaceParam.toString()+",查询业务场景各时间段接口使用量异常："+e.toString());
        }
        return returnResult;
    }

    private JSONObject selectTappDailyInterfaceGeneralPurpose(InterfaceUsageParam interfaceUsageParam,String type){
        JSONObject resultObj=new JSONObject();
        try {
            if(StringUtils.isEmpty(interfaceUsageParam.getIdentification())){
                interfaceUsageParam.setIdentification("day");
            }
            if("day".equals(interfaceUsageParam.getIdentification())){
                if(StringUtils.isEmpty(interfaceUsageParam.getCallDate())){
                    interfaceUsageParam.setCallDate(DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,false,1));
                }
            }else if("month".equals(interfaceUsageParam.getIdentification())){
                if(StringUtils.isEmpty(interfaceUsageParam.getCallDate())){
                    interfaceUsageParam.setCallDate(DateUtil.getFirstFewMonth(LocalDateUtil.nowDate("yyyy-MM"),"yyyy-MM",false,1));
                }
            }else if("year".equals(interfaceUsageParam.getIdentification())){
                if(StringUtils.isEmpty(interfaceUsageParam.getCallDate())){
                    interfaceUsageParam.setCallDate(DateUtil.getFirstFewYear(LocalDateUtil.nowDate("yyyy"),"yyyy",false,1));
                }
            }

            Long interfaceQuanStatis=0L;
            if("dptMax".equals(type)){
                //部门接口使用最多
                JSONObject jsonObjectNowMax=tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallDptMax(interfaceUsageParam.getCallDate());
                if(jsonObjectNowMax!=null){
                    log.info("method:selectTappDailyInterfaceCallDptMax,param:"+interfaceUsageParam.toString()+",部门接口使用最多统计："+jsonObjectNowMax.toString());
                    interfaceQuanStatis=jsonObjectNowMax.getLong("dptNum");
                    resultObj.put("dptName",jsonObjectNowMax.getString("DEPARTMENT"));
                }
            }else if("all".equals(type)){
                //接口总量
                Long interfaceTotal = tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallAll();
                if(interfaceTotal!=null){
                    log.info("method:selectTappDailyInterfaceCallTotal,param:"+interfaceUsageParam.toString()+",查询接口使用总量统计："+interfaceQuanStatis);
                    interfaceQuanStatis=interfaceTotal;
                }
            }else if("total".equals(type)) {
                //接口使用总量(某日/月/年)
                Long interfaceTotal = tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallTotal(interfaceUsageParam.getCallDate());
                if(interfaceTotal!=null){
                    log.info("method:selectTappDailyInterfaceCallTotal,param:"+interfaceUsageParam.toString()+",查询接口使用总量(某日/月/年)统计："+interfaceQuanStatis);
                    interfaceQuanStatis=interfaceTotal;
                }
            }else if("max".equals(type)){
                 //接口使用最多
                JSONObject jsonObjectNowMax=tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallMax(interfaceUsageParam.getCallDate());
                if(jsonObjectNowMax!=null){
                    log.info("method:selectTappDailyInterfaceCallMax,param:"+interfaceUsageParam.toString()+",查询接口使用最多统计："+jsonObjectNowMax.toString());
                    interfaceQuanStatis=jsonObjectNowMax.getLong("sumCallNo");
                    resultObj.put("interfaceName",jsonObjectNowMax.getString("BUSINESS_SENCE"));
                }
            }else if("min".equals(type)){
                //接口使用最少
                JSONObject jsonObjectNowMin=tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallMin(interfaceUsageParam.getCallDate());
                if(jsonObjectNowMin!=null){
                    log.info("method:selectTappDailyInterfaceCallMin,param:"+interfaceUsageParam.toString()+",查询接口使用最少统计："+jsonObjectNowMin.toString());
                    interfaceQuanStatis=jsonObjectNowMin.getLong("sumCallNo");
                    resultObj.put("interfaceName",jsonObjectNowMin.getString("BUSINESS_SENCE"));
                }
            }
//            String callDateStr=DateUtil.getFirstFewDays(tappDailyInterfaceParam.getCallDate(),LocalDateUtil.DATE_FORMAT_1,tappDailyInterfaceParam.getFlag(),tappDailyInterfaceParam.getDays());
//            Long adjacentInterfaceQuanStatis=0L;
//            if("total".equals(type)){
//                //与前几天或者后几天相比较
//                Long interfaceTotalLong=tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallTotal(callDateStr);
//                log.info("method:selectTappDailyInterfaceCallTotal,param:"+callDateStr+",查询接口使用总量(前几天或者后几天)统计："+interfaceTotalLong);
//                if(interfaceTotalLong!=null){
//                    adjacentInterfaceQuanStatis=interfaceTotalLong;
//                }
//            }else if("max".equals(type)){
//                JSONObject jsonObjectMax=tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallMax(callDateStr);
//                log.info("method:selectTappDailyInterfaceCallMax,param:"+callDateStr+",查询接口使用最多(前几天或者后几天)统计："+jsonObjectMax.toString());
//                if(jsonObjectMax!=null){
//                    adjacentInterfaceQuanStatis=jsonObjectMax.getLong("CALL_NO");
//                }
//            }else if("min".equals(type)){
//                JSONObject jsonObjectMin=tappDailyInterfaceCallMapper.selectTappDailyInterfaceCallMin(callDateStr);
//                log.info("method:selectTappDailyInterfaceCallMin,param:"+callDateStr+",查询接口使用最少(前几天或者后几天)统计："+jsonObjectMin.toString());
//                if(jsonObjectMin!=null){
//                    adjacentInterfaceQuanStatis=jsonObjectMin.getLong("CALL_NO");
//                }
//            }
//            String percentage="0%";
//            if(tappDailyInterfaceParam.getFlag()){
//                Long diffInterface= adjacentInterfaceQuanStatis-interfaceQuanStatis;
//                percentage= CalculateUtil.getFixRatio(diffInterface,interfaceQuanStatis);
//            }else{
//                Long adjacentDiffInterface= interfaceQuanStatis-adjacentInterfaceQuanStatis;
//                percentage=CalculateUtil.getFixRatio(adjacentDiffInterface,adjacentInterfaceQuanStatis);
//            }
            resultObj.put("interfaceQuanStatis",interfaceQuanStatis);
//            resultObj.put("flag",tappDailyInterfaceParam.getFlag());
//            resultObj.put("adjacentInterfaceQuanStatis",adjacentInterfaceQuanStatis);
//            resultObj.put("percentage",percentage);
        }catch (Exception e){
            log.error("method:selectTappDailyInterfaceGeneralPurpose,param:"+interfaceUsageParam.toString()+",查询接口使用量统计异常："+e.toString());
        }
        return resultObj;
    }
}
