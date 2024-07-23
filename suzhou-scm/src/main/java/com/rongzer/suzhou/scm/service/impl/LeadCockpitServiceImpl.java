package com.rongzer.suzhou.scm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.constant.Consts;
import com.rongzer.suzhou.scm.dao.ModCompanyMapper;
import com.rongzer.suzhou.scm.dao.RealTimeBlockChainMapper;
import com.rongzer.suzhou.scm.dao.SevenBusinessTypeMapper;
import com.rongzer.suzhou.scm.dao.TablesMapper;
import com.rongzer.suzhou.scm.pojo.RealTimeBlockChain;
import com.rongzer.suzhou.scm.pojo.param.*;
import com.rongzer.suzhou.scm.properties.CommonProperties;
import com.rongzer.suzhou.scm.service.LeadCockpitService;
import com.rongzer.suzhou.scm.service.SIPCreditService;
import com.rongzer.suzhou.scm.util.CalculateUtil;
import com.rongzer.suzhou.scm.util.DateUtil;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import com.rongzer.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/11/14 10:51
 * @Version 1.0
 **/
@Slf4j
@Service
public class LeadCockpitServiceImpl implements LeadCockpitService {

    public static final Logger bizLogger= LoggerFactory.getLogger("bizSuzhouLogger");

    @Autowired
    private SIPCreditService sipCreditService;
    @Autowired
    private CommonProperties config;
    @Autowired
    private RealTimeBlockChainMapper realTimeBlockChainMapper;
    @Autowired
    private SevenBusinessTypeMapper sevenBusinessTypeMapper;
    @Autowired
    private TablesMapper tablesMapper;
    @Autowired
    private ModCompanyMapper modCompanyMapper;

    @Override
    public ApiResponse selectCreditDataLinkTrend() {
        JSONObject resultObj=new JSONObject();
        ApiResponse apiResponseResult=new ApiResponse();
        CreditReportApplyIndexParam creditReportApplyIndexParam=new CreditReportApplyIndexParam();
        creditReportApplyIndexParam.setPageSize(1000);
        for (int i=1;i<8;i++) {
            String dataStr=DateUtil.getFirstFewDays(LocalDateUtil.dateToStr(LocalDate.now()), "yyyy-MM-dd", false, i);
            creditReportApplyIndexParam.setCreateTime(dataStr);
            ApiResponse apiResponse=sipCreditService.selectCreditReportApplyByIndex(creditReportApplyIndexParam);
            apiResponseResult.setResultCode(apiResponse.getResultCode());
            apiResponseResult.setResultMsg(apiResponse.getResultMsg());
            if("200".equals(apiResponse.getResultCode())){
                JSONObject jsonObject= (JSONObject) apiResponse.getData();
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                resultObj.put(dataStr,jsonArray.size());
            }else{
                break;
            }
        }
        apiResponseResult.setData(DateUtil.sortDateDesc(resultObj));
        return apiResponseResult;
    }

    @Override
    public ReturnResult getRowCount() {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询表格行记录总数成功");
        String tableName=config.getTableNames();
        String[] tableNames=tableName.split(",");
        Integer rowCount=0;
        for(String table:tableNames){
            ApiResponse apiResponse= Consts.client.getRowCount(table,config);
            if("200".equals(apiResponse.getResultCode())){
                rowCount+=Integer.valueOf(apiResponse.getData().toString());
            }else{
                returnResult.setStatus(0);
                returnResult.setMsg("查询表格"+table+"中行记录总数异常："+apiResponse.getResultMsg());
                log.error("查询表格"+table+"中行记录总数异常："+apiResponse.getResultMsg());
                break;
            }
        }
        returnResult.setData(rowCount);
        return returnResult;
    }

    @Override
    public ReturnResult getTableRowCount(SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询表格行记录总数成功");
        JSONObject resultObj=new JSONObject();
        if(syncRBCTableDataJobParam.getTables()==null || syncRBCTableDataJobParam.getTables().length==0){
            String tableName=config.getTableNames();
            String[] tableNames=tableName.split(",");
            syncRBCTableDataJobParam.setTables(tableNames);
        }
        for(String table:syncRBCTableDataJobParam.getTables()){
            ApiResponse apiResponse= Consts.client.getRowCount(table,config);
            if("200".equals(apiResponse.getResultCode())){
                resultObj.put(table,Integer.valueOf(apiResponse.getData().toString()));
            }else{
                returnResult.setStatus(0);
                returnResult.setMsg("查询表格"+table+"中行记录总数异常："+apiResponse.getResultMsg());
                log.error("查询表格"+table+"中行记录总数异常："+apiResponse.getResultMsg());
                break;
            }
        }
        returnResult.setData(resultObj);
        return returnResult;
    }

    @Override
    public ReturnResult selectCreditApplication(CreditApplicationParam creditApplicationParam) {
        ReturnResult returnResult=new ReturnResult();
        JSONObject resultObj=new JSONObject();
        try {
            if(StringUtils.isEmpty(creditApplicationParam.getIdentification())){
                creditApplicationParam.setIdentification("day");
            }
            if("day".equals(creditApplicationParam.getIdentification())){
                if(StringUtils.isEmpty(creditApplicationParam.getCreateDate())){
                    creditApplicationParam.setCreateDate(DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,false,1));
                }
            }else if("month".equals(creditApplicationParam.getIdentification())){
                if(StringUtils.isEmpty(creditApplicationParam.getCreateDate())){
                    creditApplicationParam.setCreateDate(DateUtil.getFirstFewMonth(LocalDateUtil.nowDate("yyyy-MM"),"yyyy-MM",false,1));
                }
            }else if("year".equals(creditApplicationParam.getIdentification())){
                if(StringUtils.isEmpty(creditApplicationParam.getCreateDate())){
                    creditApplicationParam.setCreateDate(DateUtil.getFirstFewYear(LocalDateUtil.nowDate("yyyy"),"yyyy",false,1));
                }
            }else{
                if(StringUtils.isEmpty(creditApplicationParam.getCreateDate())){
                    creditApplicationParam.setCreateDate(DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,false,1));
                }
            }

            JSONObject jsonObject=sevenBusinessTypeMapper.selectCreditDataUpchainsNumber(creditApplicationParam.getCreateDate());
            if(jsonObject!=null) {
                //双公示(行政许可（变更）信息、行政处罚信息)
                int licenseNum = jsonObject.getIntValue("ADM_LICENSE");
                int penaltyNum = jsonObject.getIntValue("ADM_PENALTY");
                resultObj.put("doubleNotice",licenseNum+penaltyNum);
                //信用综合评价
                int modNum = jsonObject.getIntValue("MOD_COMPANY");
                resultObj.put("creditEval",modNum);
                //信用承诺
                int creditNum = jsonObject.getIntValue("CREDIT_COMMITMENT_2022");
                resultObj.put("creditPromise",creditNum);
                //异议申诉
                int appealNum = jsonObject.getIntValue("T_CREDIT_OBJECTION_APPEAL_APPLY");
                resultObj.put("creditAppeal",appealNum);
                //信用修复
                int repairNum = jsonObject.getIntValue("T_CREDIT_REPAIR_APPLY");
                resultObj.put("creditRepair",repairNum);
                returnResult.setData(resultObj);
                returnResult.setStatus(1);
                returnResult.setMsg("查询公共信用应用成功");
            }else{
                returnResult.setStatus(0);
                returnResult.setMsg("查询公共信用应用为空");
                log.error("查询公共信用应用为空");
            }
            //信用综合评价
//            JSONObject creditObj=getApplicationRowCount(Consts.MOD_COMPANY);
//            JSONObject creditObj1=new JSONObject();
//            creditObj1.put("creditEval",creditObj);
//            jsonArray.add(creditObj1);
//            //双公示(行政许可（变更）信息、行政处罚信息)
//            JSONObject douNoticeObj=new JSONObject();
//            Integer rowCount=0;
//            JSONObject adminiLicenObj=getApplicationRowCount(Consts.ADM_LICENSE);
//            if(adminiLicenObj.getIntValue("status")==1){
//                rowCount=adminiLicenObj.getInteger("value");
//            }else{
//                douNoticeObj.put("message",adminiLicenObj.getString("message"));
//            }
//            JSONObject adminiPenObj=getApplicationRowCount(Consts.ADM_PENALTY);
//           if(adminiPenObj.getIntValue("status")==1){
//               rowCount+=adminiPenObj.getInteger("value");
//               douNoticeObj.put("value",rowCount);
//               douNoticeObj.put("status",1);
//           }else{
//               douNoticeObj.put("status",0);
//               douNoticeObj.put("message",douNoticeObj.getString("message")+","+adminiPenObj.getString("message"));
//           }
//            JSONObject douNoticeObj1=new JSONObject();
//            douNoticeObj1.put("doubleNotice",douNoticeObj);
//            jsonArray.add(douNoticeObj1);
//           //信用修复
//            JSONObject creditRepairObj=getApplicationRowCount(Consts.T_CREDIT_REPAIR_APPLY);
//            JSONObject creditRepairObj1=new JSONObject();
//            creditRepairObj1.put("creditRepair",creditRepairObj);
//            jsonArray.add(creditRepairObj1);
//            //信用申诉
//            JSONObject creditAppealObj=getApplicationRowCount(Consts.T_CREDIT_OBJECTION_APPEAL_APPLY);
//            JSONObject creditAppealObj1=new JSONObject();
//            creditAppealObj1.put("creditAppeal",creditAppealObj);
//            jsonArray.add(creditAppealObj1);
//            //信用承诺
//            JSONObject creditPromiseObj=getApplicationRowCount(Consts.CREDIT_COMMITMENT_2022);
//            JSONObject creditPromiseObj1=new JSONObject();
//            creditPromiseObj1.put("creditPromise",creditPromiseObj);
//            jsonArray.add(creditPromiseObj1);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询公共信用应用异常："+e.getMessage());
            log.error("查询公共信用应用异常："+e.toString());
        }
        return returnResult;
    }
    private JSONObject getApplicationRowCount(String table){
        ApiResponse apiResponse= Consts.client.getRowCount(table,config);
        JSONObject resultObj=new JSONObject();
//        resultObj.put("tableSpecies",keyName);
        if("200".equals(apiResponse.getResultCode())){
            resultObj.put("value",Integer.valueOf(apiResponse.getData().toString()));
            resultObj.put("status",1);
        }else{
            resultObj.put("status",0);
            resultObj.put("message","查询表格"+table+"中行记录总数异常："+apiResponse.getResultMsg());
            log.error("查询表格"+table+"中行记录总数异常："+apiResponse.getResultMsg());
        }
        return resultObj;
    }
    @Override
    public ReturnResult selectLegalNumByIndex() {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询企业法人表格行记录总数成功");
        Long legalRowCount=0L;
        try {
            JSONObject indexLegal=new JSONObject();
            indexLegal.put("rows.SUBJECT_TYPE","1");
            //按照主体类型查询法人数据总记录
            String subTypeLegalPersonal=config.getSubTypeLegalPersonal();
            String[] subTypeLegalPersonals=subTypeLegalPersonal.split(",");
            legalRowCount=queryTableRowNum(subTypeLegalPersonals,indexLegal);
            //共有数据，按照主体类型查询法人数据总记录
            legalRowCount=selectSubjectCreditByIndex()+legalRowCount;
            //按照行政相对人类别查询法人数据总记录
            JSONObject indexdmCat=new JSONObject();
            indexdmCat.put("rows.ADM_COUNTERPART_CAT","01");
           String admCatLegalPersonal=config.getAdmCatLegalPersonal();
            String[] admCatLegalPersonals=admCatLegalPersonal.split(",");
            legalRowCount=queryTableRowNum(admCatLegalPersonals,indexdmCat)+legalRowCount;
            JSONObject indexdmLegal=new JSONObject();
            indexdmLegal.put("rows.ADM_COUNTERPART_CAT","03");
            legalRowCount=queryTableRowNum(admCatLegalPersonals,indexdmLegal)+legalRowCount;
            //查询只包含法人数据的总记录
            String legalTable=config.getLegalTables();
            String[] legalTables=legalTable.split(",");
            legalRowCount=queryTableRowNum(legalTables,new JSONObject())+legalRowCount;
            returnResult.setData(legalRowCount);
            RealTimeBlockChain realTimeBlockChain=new RealTimeBlockChain();
            realTimeBlockChain.setUpchainType(1);
            realTimeBlockChain.setUpchainNumber(legalRowCount);
            realTimeBlockChain.setCreateDate(LocalDateUtil.nowDate("yyyy-MM-dd HH:mm"));
            realTimeBlockChain.setCreateDateTime(LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_1));
            realTimeBlockChainMapper.insertBlockChainOnChain(realTimeBlockChain);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询企业法人表格行记录总数异常："+e.getMessage());
            log.error("method:selectLegalNumByIndex,查询企业法人表格行记录总数异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectPersonalNumByIndex() {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询自然人表格行记录总数成功");
        Long personalRowCount=0L;
        try {
            JSONObject indexPersonal=new JSONObject();
            indexPersonal.put("rows.SUBJECT_TYPE","2");
            //按照主体类型查询自然人数据总记录
            String subTypeLegalPersonal=config.getSubTypeLegalPersonal();
            String[] subTypeLegalPersonals=subTypeLegalPersonal.split(",");
            personalRowCount=queryTableRowNum(subTypeLegalPersonals,indexPersonal);
            //共有数据，按照主体类型查询自然人数据总记录
            personalRowCount=selectSubjectCreditByIndex()+personalRowCount;
            //按照行政相对人类别查询自然人数据总记录
            JSONObject indexadmPersonal=new JSONObject();
            indexadmPersonal.put("rows.ADM_COUNTERPART_CAT","02");
            String admCatLegalPersonal=config.getAdmCatLegalPersonal();
            String[] admCatLegalPersonals=admCatLegalPersonal.split(",");
            personalRowCount=queryTableRowNum(admCatLegalPersonals,indexadmPersonal)+personalRowCount;
            returnResult.setData(personalRowCount);
            RealTimeBlockChain realTimeBlockChain=new RealTimeBlockChain();
            realTimeBlockChain.setUpchainType(2);
            realTimeBlockChain.setUpchainNumber(personalRowCount);
            realTimeBlockChain.setCreateDate(LocalDateUtil.nowDate("yyyy-MM-dd HH:mm"));
            realTimeBlockChain.setCreateDateTime(LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_1));
            realTimeBlockChainMapper.insertBlockChainOnChain(realTimeBlockChain);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询自然人表格行记录总数异常："+e.getMessage());
            log.error("method:selectPersonalNumByIndex,查询自然人表格行记录总数异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectUpchainsNumber(RealTimeBlockChainParam realTimeBlockChainParam) {
        ReturnResult returnResult=new ReturnResult();
        try {
//            if(StringUtils.isEmpty(realTimeBlockChainParam.getCreateDate())){
//                realTimeBlockChainParam.setCreateDate(DateUtil.getFirstFewMinutes(LocalDateUtil.nowDate("yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm",false,1));
//            }
            returnResult.setStatus(1);
            returnResult.setMsg("查询信用业务上链数据总累计成功");
            JSONObject resultObj=new JSONObject();
            //前端默认传3  查询总量
            Integer upchainType=realTimeBlockChainParam.getUpchainType();
            Long upchainsNumber = realTimeBlockChainMapper.selectUpchainsNumber(upchainType);
            resultObj.put("legalUpchainsNumber",0);
            resultObj.put("personalUpchainsNumber",0);
            resultObj.put("upchainsNumberTotal",upchainsNumber);
            returnResult.setData(resultObj);

            /*if(upchainType==1 || upchainType==2) {
                Long upchainsNumber = realTimeBlockChainMapper.selectUpchainsNumber(upchainType);
                if(upchainsNumber==null){
                    upchainsNumber=0L;
                }
                resultObj.put("upchainsNumber",upchainsNumber);
                returnResult.setData(resultObj);
            }else if(upchainType==3){
                Long legalUpchainsNumber = realTimeBlockChainMapper.selectUpchainsNumber(Consts.LEGAL_UPCHAINS_TYPE);
                if(legalUpchainsNumber==null){
                    legalUpchainsNumber=0L;
                }
                Long personalUpchainsNumber = realTimeBlockChainMapper.selectUpchainsNumber(Consts.PERSONAL_UPCHAINS_TYPE);
                if(personalUpchainsNumber==null){
                    personalUpchainsNumber=0L;
                }
                Long upchainsNumberTotal=legalUpchainsNumber+personalUpchainsNumber;
                resultObj.put("legalUpchainsNumber",legalUpchainsNumber);
                resultObj.put("personalUpchainsNumber",personalUpchainsNumber);
                resultObj.put("upchainsNumberTotal",upchainsNumberTotal);
                returnResult.setData(resultObj);
            }else{
                returnResult.setStatus(0);
                returnResult.setMsg("参数上链类型异常");
            }*/
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询信用业务上链数据总累计异常："+e.getMessage());
            log.error("method:selectUpchainsNumber,param:"+realTimeBlockChainParam.toString()+",查询信用业务上链数据总累计异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectSevenBusinessUpchainsNumber() {
        ReturnResult returnResult=new ReturnResult();
        try {
            returnResult.setStatus(1);
            returnResult.setMsg("查询七大信用业务上链数据累计成功");
            JSONObject resultObj=new JSONObject();
            //4:信用综合评价 5:信用报告 6:信用审查 7:信用承诺 8:信用修复 9:异议申诉 10:行政许可 11:行政处罚
//            Long modNumber = realTimeBlockChainMapper.selectUpchainsNumber(4);
            Long modNumber=modCompanyMapper.selectModCompanyAllNum();
            resultObj.put("MOD_COMPANY",modNumber==null?0:modNumber);
//            Long reportNumber = realTimeBlockChainMapper.selectUpchainsNumber(5);
            Long reportNumber =sevenBusinessTypeMapper.selectTCreditReportApplyAllNum();
            resultObj.put("T_CREDIT_REPORT_APPLY",reportNumber==null?0:reportNumber);
//            Long reviewNumber = realTimeBlockChainMapper.selectUpchainsNumber(6);
            Long reviewNumber=sevenBusinessTypeMapper.selectTCreditReviewApplyAllNum();
            resultObj.put("T_CREDIT_REVIEW_APPLY",reviewNumber==null?0:reviewNumber);
//            Long creditNumber = realTimeBlockChainMapper.selectUpchainsNumber(7);
            Long creditNumber = sevenBusinessTypeMapper.selectCreditCommitmentAllNum();
            resultObj.put("CREDIT_COMMITMENT_2022",creditNumber==null?0:creditNumber);
//            Long repairNumber = realTimeBlockChainMapper.selectUpchainsNumber(8);
            Long repairNumber =sevenBusinessTypeMapper.selectTCreditRepairApplyAllNum();
            resultObj.put("T_CREDIT_REPAIR_APPLY",repairNumber==null?0:repairNumber);
//            Long appealNumber = realTimeBlockChainMapper.selectUpchainsNumber(9);
            Long appealNumber =sevenBusinessTypeMapper.selectTCreditObjectionAppealApplyAllNum();
            resultObj.put("T_CREDIT_OBJECTION_APPEAL_APPLY",appealNumber==null?0:appealNumber);
//            Long licenseNumber = realTimeBlockChainMapper.selectUpchainsNumber(10);
            Long licenseNumber=sevenBusinessTypeMapper.selectADMLicenseAllNum();
            resultObj.put("ADM_LICENSE",licenseNumber==null?0:licenseNumber);
//            Long penaltyNumber = realTimeBlockChainMapper.selectUpchainsNumber(11);
            Long penaltyNumber =sevenBusinessTypeMapper.selectADMPENALTYAllNum();
            resultObj.put("ADM_PENALTY",penaltyNumber==null?0:penaltyNumber);
            returnResult.setData(resultObj);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询七大信用业务上链数据累计异常："+e.getMessage());
            log.error("method:selectSevenBusinessUpchainsNumber,查询七大信用业务上链数据累计异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectCreditDataUpchainsNumber(CreditDataUpchainsParam creditDataUpchainsParam) {
        ReturnResult returnResult=new ReturnResult();
        JSONObject resultObj=new JSONObject();
        try {
            if(StringUtils.isEmpty(creditDataUpchainsParam.getIdentification())){
                creditDataUpchainsParam.setIdentification("day");
            }
            if("day".equals(creditDataUpchainsParam.getIdentification())){
                if(StringUtils.isEmpty(creditDataUpchainsParam.getCreateDate())){
                    creditDataUpchainsParam.setCreateDate(DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,creditDataUpchainsParam.getFlag(),1));
                }
            }else if("month".equals(creditDataUpchainsParam.getIdentification())){
                if(StringUtils.isEmpty(creditDataUpchainsParam.getCreateDate())){
                    creditDataUpchainsParam.setCreateDate(DateUtil.getFirstFewMonth(LocalDateUtil.nowDate("yyyy-MM"),"yyyy-MM",creditDataUpchainsParam.getFlag(),1));
                }
            }else if("year".equals(creditDataUpchainsParam.getIdentification())){
                if(StringUtils.isEmpty(creditDataUpchainsParam.getCreateDate())){
                    creditDataUpchainsParam.setCreateDate(DateUtil.getFirstFewYear(LocalDateUtil.nowDate("yyyy"),"yyyy",creditDataUpchainsParam.getFlag(),1));
                }
            }else{
                if(StringUtils.isEmpty(creditDataUpchainsParam.getCreateDate())){
                    creditDataUpchainsParam.setCreateDate(DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,creditDataUpchainsParam.getFlag(),1));
                }
            }

            JSONObject jsonObject=sevenBusinessTypeMapper.selectCreditDataUpchainsNumber(creditDataUpchainsParam.getCreateDate());
            int num=getSevenBusinessNum(jsonObject);
            resultObj.put(creditDataUpchainsParam.getCreateDate(),num);
            for(int i=1;i<creditDataUpchainsParam.getDays();i++){
                if("day".equals(creditDataUpchainsParam.getIdentification())){
                    String createTime1=DateUtil.getFirstFewDays(creditDataUpchainsParam.getCreateDate(),LocalDateUtil.DATE_FORMAT_1,creditDataUpchainsParam.getFlag(),i);
                    JSONObject object1=sevenBusinessTypeMapper.selectCreditDataUpchainsNumber(createTime1);
                    int num1=getSevenBusinessNum(object1);
                    resultObj.put(createTime1,num1);
                }else if("month".equals(creditDataUpchainsParam.getIdentification())){
                    String createTime2=DateUtil.getFirstFewMonth(creditDataUpchainsParam.getCreateDate(),"yyyy-MM",creditDataUpchainsParam.getFlag(),i);
                    JSONObject object2=sevenBusinessTypeMapper.selectCreditDataUpchainsNumber(createTime2);
                    int num2=getSevenBusinessNum(object2);
                    resultObj.put(createTime2,num2);
                }else if("year".equals(creditDataUpchainsParam.getIdentification())){
                    String createTime3=DateUtil.getFirstFewYear(creditDataUpchainsParam.getCreateDate(),"yyyy",creditDataUpchainsParam.getFlag(),i);
                    JSONObject object3=sevenBusinessTypeMapper.selectCreditDataUpchainsNumber(createTime3);
                    int num3=getSevenBusinessNum(object3);
                    resultObj.put(createTime3,num3);
                }

            }
            returnResult.setStatus(1);
            returnResult.setMsg("查询信用数据上链趋势成功");
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询信用数据上链趋势异常："+e.getMessage());
            log.error("method:selectCreditDataUpchainsNumber,param:"+creditDataUpchainsParam.toString()+",查询信用数据上链趋势异常："+e.toString());
        }
        returnResult.setData(DateUtil.sortDateDesc(resultObj));
        return returnResult;
    }
    private int getSevenBusinessNum(JSONObject jsonObject){
        int num=0;
        if(jsonObject!=null) {
            int licenseNum = jsonObject.getIntValue("ADM_LICENSE");
            int penaltyNum = jsonObject.getIntValue("ADM_PENALTY");
            int modNum = jsonObject.getIntValue("MOD_COMPANY");
            int creditNum = jsonObject.getIntValue("CREDIT_COMMITMENT_2022");
            int appealNum = jsonObject.getIntValue("T_CREDIT_OBJECTION_APPEAL_APPLY");
            int repairNum = jsonObject.getIntValue("T_CREDIT_REPAIR_APPLY");
            int reportNum = jsonObject.getIntValue("T_CREDIT_REPORT_APPLY");
            int reviewNum = jsonObject.getIntValue("T_CREDIT_REVIEW_APPLY");
            num = licenseNum + penaltyNum + modNum + creditNum + appealNum + repairNum + reportNum + reviewNum;
        }
        return num;
    }
    @Override
    public ReturnResult selectOnChainGrowthTrend(CreditDataUpchainsParam creditDataUpchainsParam) {
        ReturnResult returnResult=new ReturnResult();
        JSONObject resultObj=new JSONObject();
        try {
            /**
             *截至到昨天上链总量
             */
            if(StringUtils.isEmpty(creditDataUpchainsParam.getCreateDate())){
                creditDataUpchainsParam.setCreateDate(DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,creditDataUpchainsParam.getFlag(),1));
            }
            Long num=realTimeBlockChainMapper.selectCreditDataUpchainsNumber(creditDataUpchainsParam.getCreateDate());
            if(num==null){
                num=0L;
            }
            resultObj.put("specifyTime",num);
            /**
             * 截至到前天上链总量
             */
            String createTime=DateUtil.getFirstFewDays(creditDataUpchainsParam.getCreateDate(),LocalDateUtil.DATE_FORMAT_1,creditDataUpchainsParam.getFlag(),creditDataUpchainsParam.getDays());
            Long num1=realTimeBlockChainMapper.selectCreditDataUpchainsNumber(createTime);
            if(num1==null){
                num1=0L;
            }
            /**
             * 截至到大前天上链总量
             */
            String preCreateTime=DateUtil.getFirstFewDays(creditDataUpchainsParam.getCreateDate(),LocalDateUtil.DATE_FORMAT_1,creditDataUpchainsParam.getFlag(),creditDataUpchainsParam.getDays()+1);
            Long preNum=realTimeBlockChainMapper.selectCreditDataUpchainsNumber(preCreateTime);
            if(preNum==null){
                preNum=0L;
            }
            /**
             * 昨天上链总量
             */
            resultObj.put("timeApart",num-num1);

            //CalculateUtil.getFixRatio((num-num1)-(num1-preNum),num1-preNum)
            //resultObj.put("growthTrend", CalculateUtil.getRatio(num,num1));
            resultObj.put("growthTrend", CalculateUtil.getFixRatio((num-num1)-(num1-preNum),num1-preNum));
            returnResult.setStatus(1);
            returnResult.setMsg("查询上链增长趋势成功");
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询上链增长趋势异常："+e.getMessage());
            log.error("method:selectOnChainGrowthTrend,param:"+creditDataUpchainsParam.toString()+",查询上链增长趋势异常："+e.toString());
        }
        returnResult.setData(resultObj);
        return returnResult;
    }

    @Override
    public ReturnResult selectRealTimeDataUpchainsNumber(RealTimeDataUpchainsParam realTimeDataUpchainsParam) {
        ReturnResult returnResult=new ReturnResult();
        JSONObject resultObj=new JSONObject();
        try {
            if(StringUtils.isEmpty(realTimeDataUpchainsParam.getCreateTime())){
                realTimeDataUpchainsParam.setCreateTime(DateUtil.getFirstFewMinutes(LocalDateUtil.nowDate("yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm",realTimeDataUpchainsParam.getFlag(),1));
            }
            Long num=realTimeBlockChainMapper.selectRealTimeDataUpchainsNumber(realTimeDataUpchainsParam.getCreateTime());
            if(num==null){
                num=0L;
            }
            resultObj.put(realTimeDataUpchainsParam.getCreateTime().substring(11),num);
            for(int i=1;i<realTimeDataUpchainsParam.getMinutes();i++){
                String createTime=DateUtil.getFirstFewMinutes(realTimeDataUpchainsParam.getCreateTime(),"yyyy-MM-dd HH:mm",realTimeDataUpchainsParam.getFlag(),i);
                Long num1=realTimeBlockChainMapper.selectRealTimeDataUpchainsNumber(createTime);
                if(num1==null){
                    num1=0L;
                }
                resultObj.put(createTime.substring(11),num1);
            }
            returnResult.setStatus(1);
            returnResult.setMsg("查询实时上链记录总数成功");
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询实时上链记录总数异常："+e.getMessage());
            log.error("method:selectRealTimeDataUpchainsNumber,param:"+realTimeDataUpchainsParam.toString()+",查询实时上链记录总数异常："+e.toString());
        }
        returnResult.setData(DateUtil.sortDateDesc(resultObj));
        return returnResult;
    }

    @Override
    public ReturnResult selectEnterpriseInfoByUscc(ForEnterprisesParam forEnterprisesParam) {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("通过统一社会信用代码查询企业评分等级信息");
        JSONObject resultObj=new JSONObject();
        try {
            JSONObject indexMod=new JSONObject();
            indexMod.put("rows.USCC",forEnterprisesParam.getSocialCreditCode());
            //法人综合信用评价信息
            JSONObject modObject =queryEnterpriseInfoByUscc(indexMod,"MOD_COMPANY");
            if (modObject.getIntValue("status") ==1) {
                JSONArray arrayList=modObject.getJSONArray("data");
                if(arrayList.size()>0) {
                    JSONObject object = arrayList.getJSONObject(0);
                    JSONObject rowObj = object.getJSONObject("rows");
                    resultObj.put("ratingLevel", rowObj.getString("RATING_LEVEL_NAME"));
                    resultObj.put("assessScore", rowObj.getString("ASSESS_SCORE"));
                    resultObj.put("assessDate", rowObj.getString("ASSESS_UPDATE_DATE"));
                    resultObj.put("uscc", rowObj.getString("USCC"));
                    resultObj.put("name", rowObj.getString("NAME"));
                }else {
                    resultObj.put("ratingLevel", "");
                    resultObj.put("assessScore", "");
                    resultObj.put("assessDate", "");
                    resultObj.put("uscc", forEnterprisesParam.getSocialCreditCode());
                    resultObj.put("name", "");
                }
            }else {
                returnResult.setStatus(0);
                returnResult.setMsg(modObject.getString("msg"));
            }
            //审查结果表
            JSONObject objectionObject =queryEnterpriseInfoByUscc(indexMod,"T_CREDIT_REVIEW_APPLY_LIST");
            if (objectionObject.getIntValue("status") ==1) {
                JSONArray arrayList1=objectionObject.getJSONArray("data");
                if(arrayList1.size()>0) {
                    JSONObject object1 = arrayList1.getJSONObject(0);
                    JSONObject rowObj1 = object1.getJSONObject("rows");
                    //严重失信数量
                    resultObj.put("seriousDiscredit", rowObj1.getIntValue("SERIOUS_DISCREDIT"));
                    //较重失信数量
                    resultObj.put("heavierSerious", rowObj1.getIntValue("HEAVIER_SERIOUS_DISCRED"));
                    //一般失信数量
                    resultObj.put("generalDiscredit", rowObj1.getIntValue("GENERAL_DISCREDIT"));
                    //信用审查申请表ID
                    String reviewId=rowObj1.getString("REVIEW_ID");
                    JSONObject indexReview=new JSONObject();
                    indexReview.put("rows.ID",reviewId);
                    //审查申请表
                    JSONObject reviewObj =queryEnterpriseInfoByUscc(indexReview,"T_CREDIT_REVIEW_APPLY");
                    if (reviewObj.getIntValue("status") ==1) {
                        JSONArray reviewArrayList = reviewObj.getJSONArray("data");
                        if (reviewArrayList.size() > 0) {
                            JSONObject reviewObject = reviewArrayList.getJSONObject(0);
                            JSONObject reviewrowObj = reviewObject.getJSONObject("rows");
                            //申请用途（地图中审查类别）
                            resultObj.put("applyMatter", reviewrowObj.getString("APPLY_MATTER"));
                            //审核时间（地图中信用审查时间）
                            resultObj.put("auditTime", reviewrowObj.getString("AUDIT_TIME"));
                        }else{
                            resultObj.put("applyMatter","");
                            resultObj.put("auditTime","");
                        }
                    }else{
                        returnResult.setStatus(0);
                        returnResult.setMsg(reviewObj.getString("msg"));
                    }
                }else {
                    resultObj.put("seriousDiscredit", 0);
                    resultObj.put("heavierSerious", 0);
                    resultObj.put("generalDiscredit", 0);
                    resultObj.put("applyMatter","");
                    resultObj.put("auditTime","");
                }
            }else {
                returnResult.setStatus(0);
                returnResult.setMsg(objectionObject.getString("msg"));
            }
            //双公示
            //行政许可（变更）信息
            JSONObject admLicenseObject =queryEnterpriseInfoByUscc(indexMod,"ADM_LICENSE");
            if (admLicenseObject.getIntValue("status") ==1) {
                JSONArray arrayList2=admLicenseObject.getJSONArray("data");
                resultObj.put("admLicenseNum",arrayList2.size());
            }else {
                returnResult.setStatus(0);
                returnResult.setMsg(admLicenseObject.getString("msg"));
            }
            //行政处罚信息
            JSONObject admPenaltyObject =queryEnterpriseInfoByUscc(indexMod,"ADM_PENALTY");
            if (admPenaltyObject.getIntValue("status") ==1) {
                JSONArray arrayList3=admPenaltyObject.getJSONArray("data");
                resultObj.put("admPenaltyNum",arrayList3.size());
            }else {
                returnResult.setStatus(0);
                returnResult.setMsg(admPenaltyObject.getString("msg"));
            }
            //信用修复记录表
            JSONObject repairObject =queryEnterpriseInfoByUscc(indexMod,"T_CREDIT_REPAIR_RECORD");
            if (repairObject.getIntValue("status") ==1) {
                JSONArray arrayList4=repairObject.getJSONArray("data");
                //累计修复次数
                resultObj.put("repairNum",arrayList4.size());
                if (arrayList4.size() > 0) {
                    List<String> retStr=new ArrayList();
                    for(int i=0;i<arrayList4.size();i++){
                        JSONObject objectRp = arrayList4.getJSONObject(i);
                        JSONObject repairrowObj = objectRp.getJSONObject("rows");
                        retStr.add(repairrowObj.getString("REPAIR_FINAL_TIME"));
                    }
                    Collections.sort(retStr);
                    resultObj.put("repairFinalTime",retStr.get(retStr.size()-1));
                }else{
                    //最近一次修复时间
                    resultObj.put("repairFinalTime","");
                }
            }else {
                returnResult.setStatus(0);
                returnResult.setMsg(repairObject.getString("msg"));
            }
            returnResult.setData(resultObj);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("通过统一社会信用代码查询企业评分等级信息异常："+e.getMessage());
            log.error("通过统一社会信用代码查询企业评分等级信息异常"+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectEnterpriseMysqlByUscc(ForEnterprisesParam forEnterprisesParam) {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("通过统一社会信用代码查询企业评分等级信息");
        JSONObject resultObj=new JSONObject();
        try {
            //法人综合信用评价信息
            List<JSONObject> modObject=tablesMapper.selectSoleTableDataByIndex("MOD_COMPANY","USCC",forEnterprisesParam.getSocialCreditCode(),"");
            if (modObject!=null) {
                if(modObject.size()>0) {
                    JSONObject rowObj = modObject.get(0);
                    resultObj.put("ratingLevel", rowObj.getString("RATING_LEVEL_NAME"));
                    resultObj.put("assessScore", rowObj.getString("ASSESS_SCORE"));
                    resultObj.put("assessDate", rowObj.getString("ASSESS_UPDATE_DATE"));
                    resultObj.put("uscc", rowObj.getString("USCC"));
                    resultObj.put("name", rowObj.getString("NAME"));
                }else {
                    resultObj.put("ratingLevel", "");
                    resultObj.put("assessScore", "");
                    resultObj.put("assessDate", "");
                    resultObj.put("uscc", forEnterprisesParam.getSocialCreditCode());
                    resultObj.put("name", "");
                }
            }else {
                    resultObj.put("ratingLevel", "");
                    resultObj.put("assessScore", "");
                    resultObj.put("assessDate", "");
                    resultObj.put("uscc", forEnterprisesParam.getSocialCreditCode());
                    resultObj.put("name", "");
            }
            //审查结果表
            List<JSONObject> objectionObject=tablesMapper.selectSoleTableDataByIndex("T_CREDIT_REVIEW_APPLY_LIST","USCC",forEnterprisesParam.getSocialCreditCode(),"CREATE_TIME");
            if (objectionObject!=null) {
                if(objectionObject.size()>0) {
                    JSONObject rowObj1 =objectionObject.get(0);
                    //严重失信数量
                    resultObj.put("seriousDiscredit", rowObj1.getIntValue("SERIOUS_DISCREDIT"));
                    //较重失信数量
                    resultObj.put("heavierSerious", rowObj1.getIntValue("HEAVIER_SERIOUS_DISCRED"));
                    //一般失信数量
                    resultObj.put("generalDiscredit", rowObj1.getIntValue("GENERAL_DISCREDIT"));
                    //信用审查申请表ID
                    String reviewId=rowObj1.getString("REVIEW_ID");
                    //审查申请表
                    List<JSONObject> reviewObj=tablesMapper.selectSoleTableDataByIndex("T_CREDIT_REVIEW_APPLY","ID",reviewId,"");
                    if (reviewObj!=null) {
                        if (reviewObj.size() > 0) {
                            JSONObject reviewrowObj=reviewObj.get(0);
                            //申请用途（地图中审查类别）
                            resultObj.put("applyMatter", reviewrowObj.getString("APPLY_MATTER"));
                            if("null".equals(reviewrowObj.getString("AUDIT_TIME"))){
                                //审核时间（地图中信用审查时间）
                                resultObj.put("auditTime", "");
                            }else {
                                //审核时间（地图中信用审查时间）
                                resultObj.put("auditTime", reviewrowObj.getString("AUDIT_TIME"));
                            }
                        }else{
                            resultObj.put("applyMatter","");
                            resultObj.put("auditTime","");
                        }
                    }else{
                        resultObj.put("applyMatter","");
                        resultObj.put("auditTime","");
                    }
                }else {
                    resultObj.put("seriousDiscredit", 0);
                    resultObj.put("heavierSerious", 0);
                    resultObj.put("generalDiscredit", 0);
                    resultObj.put("applyMatter","");
                    resultObj.put("auditTime","");
                }
            }else {
                resultObj.put("seriousDiscredit", 0);
                resultObj.put("heavierSerious", 0);
                resultObj.put("generalDiscredit", 0);
                resultObj.put("applyMatter","");
                resultObj.put("auditTime","");
            }
            //双公示
            //行政许可（变更）信息
            List<JSONObject> admLicenseObject =tablesMapper.selectSoleTableDataByIndex("ADM_LICENSE","USCC",forEnterprisesParam.getSocialCreditCode(),"");
            if(admLicenseObject!=null) {
                resultObj.put("admLicenseNum", admLicenseObject.size());
            }else{
                resultObj.put("admLicenseNum", 0);
            }
            //行政处罚信息
            List<JSONObject> admPenaltyObject =tablesMapper.selectSoleTableDataByIndex("ADM_PENALTY","USCC",forEnterprisesParam.getSocialCreditCode(),"");
            if(admPenaltyObject!=null) {
                resultObj.put("admPenaltyNum", admPenaltyObject.size());
            }else{
                resultObj.put("admPenaltyNum", 0);
            }
            //信用修复记录表
            List<JSONObject> repairObject =tablesMapper.selectSoleTableDataByIndex("T_CREDIT_REPAIR_RECORD","USCC",forEnterprisesParam.getSocialCreditCode(),"REPAIR_FINAL_TIME");
            if (repairObject!=null) {
                //累计修复次数
                resultObj.put("repairNum",repairObject.size());
                if (repairObject.size() > 0) {
                    JSONObject objectRp = repairObject.get(0);
                    if("null".equals(objectRp.getString("REPAIR_FINAL_TIME"))){
                        resultObj.put("repairFinalTime","");
                    }else{
                        resultObj.put("repairFinalTime",objectRp.getString("REPAIR_FINAL_TIME"));
                    }
//                    List<String> retStr=new ArrayList();
//                    for(int i=0;i<repairObject.size();i++){
//                        JSONObject objectRp = repairObject.get(i);
//                        retStr.add(objectRp.getString("REPAIR_FINAL_TIME"));
//                    }
//                    Collections.sort(retStr);
//                    resultObj.put("repairFinalTime",retStr.get(retStr.size()-1));
                }else{
                    //最近一次修复时间
                    resultObj.put("repairFinalTime","");
                }
            }else {
                resultObj.put("repairNum",0);
                //最近一次修复时间
                resultObj.put("repairFinalTime","");
            }
            returnResult.setData(resultObj);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("通过统一社会信用代码查询企业评分等级信息异常："+e.getMessage());
            log.error("通过统一社会信用代码查询企业评分等级信息异常"+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectBusinessTypeNum() {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询7大业务类型记录总数成功");
        try {
           //4:信用综合评价 5:信用报告 6:信用审查 7:信用承诺 8:信用修复 9:异议申诉 10:行政许可 11:行政处罚
            //4:信用综合评价数据的上链情况
            queryBusinessRowCount("MOD_COMPANY",4,"信用综合评价");
            //5:信用报告
            queryBusinessRowCount("T_CREDIT_REPORT_APPLY",5,"信用报告");
            //6:信用审查
            queryBusinessRowCount("T_CREDIT_REVIEW_APPLY",6,"信用审查");
            //7:信用承诺
            queryBusinessRowCount("CREDIT_COMMITMENT_2022",7,"信用承诺");
            //8:信用修复
            queryBusinessRowCount("T_CREDIT_REPAIR_APPLY",8,"信用修复");
            //9:异议申诉
            queryBusinessRowCount("T_CREDIT_OBJECTION_APPEAL_APPLY",9,"异议申诉");
            //10:行政许可
            queryBusinessRowCount("ADM_LICENSE",10,"行政许可");
            //11:行政处罚
            queryBusinessRowCount("ADM_PENALTY",11,"行政处罚");
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询7大业务类型记录总数异常："+e.getMessage());
            log.error("method:selectBusinessTypeNum,查询7大业务类型记录总数异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult selectTableDataBySyncDate(SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询表格数据成功");
        JSONObject resultObj=new JSONObject();
        if(syncRBCTableDataJobParam.getTables()==null || syncRBCTableDataJobParam.getTables().length==0){
            String tableName=config.getTableNames();
            String[] tableNames=tableName.split(",");
            syncRBCTableDataJobParam.setTables(tableNames);
        }
        if(StringUtils.isEmpty(syncRBCTableDataJobParam.getSyncDate())){
            syncRBCTableDataJobParam.setSyncDate(LocalDateUtil.dateToStr(LocalDate.now()));
        }
        JSONObject indexMod=new JSONObject();
        indexMod.put("rows.SYNC_DATE",syncRBCTableDataJobParam.getSyncDate());
        String bookMark="";
        if(StringUtils.isNotEmpty(syncRBCTableDataJobParam.getBookmark())){
            bookMark=syncRBCTableDataJobParam.getBookmark();
        }
        for(String table:syncRBCTableDataJobParam.getTables()){
            ApiResponse response=Consts.client.selectPageByIndex(table,indexMod,bookMark,Consts.WIDERANGE_ROW_NUM,config);
            if (response == null) {
                resultObj.put("status",0);
                resultObj.put("msg","未查询到该表格"+table+"数据");
                log.error("未查询到该表格"+table+"数据");
            } else if("200".equals(response.getResultCode())){
                JSONObject jsonObject = (JSONObject) response.getData();
                JSONArray arrayList = jsonObject.getJSONArray("results");
                int tableDataNum=arrayList.size();
                String bookmark=jsonObject.getString("bookmark");
                resultObj.put("status",1);
                resultObj.put(table,arrayList);
                resultObj.put("tableDataNum",tableDataNum);
                resultObj.put("bookmark",bookmark);
            }else{
                resultObj.put("status",0);
                resultObj.put("msg","查询到该表格"+table+"异常："+response.getResultMsg());
                log.error("查询到该表格"+table+"异常："+response.getResultMsg());
            }
        }
        returnResult.setData(resultObj);
        return returnResult;
    }

    @Override
    public ReturnResult selectTableDataNumBySyncDate(SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询表格数据数量成功");
        if(syncRBCTableDataJobParam.getTables()==null || syncRBCTableDataJobParam.getTables().length==0){
            returnResult.setStatus(0);
            returnResult.setMsg("表格不能为空");
            return returnResult;
        }
        if(StringUtils.isEmpty(syncRBCTableDataJobParam.getSyncDate())){
            syncRBCTableDataJobParam.setSyncDate(LocalDateUtil.dateToStr(LocalDate.now()));
        }
        JSONObject indexMod=new JSONObject();
        indexMod.put("rows.SYNC_DATE",syncRBCTableDataJobParam.getSyncDate());
//        String bookMark="";
//        if(StringUtils.isNotEmpty(syncRBCTableDataJobParam.getBookmark())){
//            bookMark=syncRBCTableDataJobParam.getBookmark();
//        }
        JSONArray tableNumArray=new JSONArray();
        for(int i=0;i<syncRBCTableDataJobParam.getTables().length;i++){
            String table=syncRBCTableDataJobParam.getTables()[i];
            //是否有下一页 true有 false无
            boolean hasNextPage = false;
            String bookMark="";
            JSONObject resultJson=new JSONObject();
            ApiResponse response=Consts.client.selectPageByIndex(table,indexMod,bookMark,Consts.ROW_NUM,config);
            if (response == null) {
                resultJson.put("status",0);
                resultJson.put("msg","未查询到该表格"+table+"数据数量");
                bizLogger.error("未查询到该表格"+table+"数据数量");
            } else if("200".equals(response.getResultCode())){
                JSONObject jsonObject = (JSONObject) response.getData();
                JSONArray arrayList = jsonObject.getJSONArray("results");
                int tableDataNum=arrayList.size();
                JSONObject pageObj=nextPage(response,table,indexMod,Consts.ROW_NUM);
                hasNextPage=pageObj.getBooleanValue("hasNextPage");
//                if(hasNextPage){
//                    bookMark=jsonObject.getString("bookmark");
//                }
                while (hasNextPage){
                    ApiResponse response1= (ApiResponse) pageObj.get("apiResponse");
//                    ApiResponse response1=Consts.client.selectPageByIndex(table,indexMod,bookMark,Consts.ROW_NUM,config);
                    if(response1==null){
                        bizLogger.info("根据索引"+indexMod.toString()+",查询"+table+"数据为空");
                        break;
                    }else if("200".equals(response1.getResultCode())) {
                        JSONObject object = (JSONObject) response1.getData();
                        JSONArray arrayList1 = object.getJSONArray("results");
                        tableDataNum+=arrayList1.size();
                        pageObj=nextPage(response1,table,indexMod,Consts.ROW_NUM);
                        hasNextPage=pageObj.getBooleanValue("hasNextPage");
                        bizLogger.info("查询"+table+"数据是否还有下一页："+hasNextPage);
                        if(!hasNextPage){
                            break;
                        }
                    }else{
                        bizLogger.info("查询"+table+"数据异常自动跳出循环："+response1.getResultCode()+",msg:"+response1.getResultMsg());
                        break;
                    }
                }
                resultJson.put("status",1);
                resultJson.put(table,tableDataNum);
            }else{
                resultJson.put("status",0);
                resultJson.put("msg","查询到该表格"+table+"数据数量异常："+response.getResultMsg());
                bizLogger.error("查询到该表格"+table+"数据数量异常："+response.getResultMsg());
            }
            tableNumArray.add(resultJson);
        }
        returnResult.setData(tableNumArray);
        return returnResult;
    }
    @Override
    public ReturnResult selectTableDataByIndex(SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=new ReturnResult();
        if(syncRBCTableDataJobParam.getTables()==null || syncRBCTableDataJobParam.getTables().length==0){
            returnResult.setStatus(0);
            returnResult.setMsg("表格不能为空");
            return returnResult;
        }
        JSONObject indexMod=new JSONObject();
        indexMod.put(syncRBCTableDataJobParam.getIndexName(),syncRBCTableDataJobParam.getIndexValue());
        JSONObject resultObj=new JSONObject();
        String bookMark="";
        if(StringUtils.isNotEmpty(syncRBCTableDataJobParam.getBookmark())){
            bookMark=syncRBCTableDataJobParam.getBookmark();
        }
        for(String table:syncRBCTableDataJobParam.getTables()){
            ApiResponse response=Consts.client.selectPageByIndex(table,indexMod,bookMark,Consts.WIDERANGE_ROW_NUM,config);
            if (response == null) {
                resultObj.put("status",0);
                resultObj.put("msg","未查询到该表格"+table+"数据");
                log.error("未查询到该表格"+table+"数据");
            } else if("200".equals(response.getResultCode())){
                JSONObject jsonObject = (JSONObject) response.getData();
                JSONArray arrayList = jsonObject.getJSONArray("results");
                int tableDataNum=arrayList.size();
                String bookmark=jsonObject.getString("bookmark");
                resultObj.put("status",1);
                resultObj.put(table,arrayList);
                resultObj.put("tableDataNum",tableDataNum);
                resultObj.put("bookmark",bookmark);
            }else{
                resultObj.put("status",0);
                resultObj.put("msg","查询到该表格"+table+"异常："+response.getResultMsg());
                log.error("查询到该表格"+table+"异常："+response.getResultMsg());
            }
        }
        returnResult.setStatus(1);
        returnResult.setMsg("查询表格数据成功");
        returnResult.setData(resultObj);
        return returnResult;
    }

    @Override
    public ReturnResult selectTableDataBySort(SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=new ReturnResult();
        if(syncRBCTableDataJobParam.getTables()==null || syncRBCTableDataJobParam.getTables().length==0){
            returnResult.setStatus(0);
            returnResult.setMsg("表格不能为空");
            return returnResult;
        }
        JSONArray sortArray = new JSONArray();
        JSONObject sortObject = new JSONObject();
        sortObject.put(syncRBCTableDataJobParam.getSortField(), "desc");
        sortArray.add(sortObject);
        JSONObject resultObj=new JSONObject();
        String bookMark="";
        if(StringUtils.isNotEmpty(syncRBCTableDataJobParam.getBookmark())){
            bookMark=syncRBCTableDataJobParam.getBookmark();
        }
        for(String table:syncRBCTableDataJobParam.getTables()){
            ApiResponse response=Consts.client.selectPageBySort(table,sortArray,bookMark,Consts.ROW_NUM,config);
            if (response == null) {
                resultObj.put("status",0);
                resultObj.put("msg","未查询到该表格"+table+"数据");
                log.error("未查询到该表格"+table+"数据");
            } else if("200".equals(response.getResultCode())){
                JSONObject jsonObject = (JSONObject) response.getData();
                JSONArray arrayList = jsonObject.getJSONArray("results");
                int tableDataNum=arrayList.size();
                String bookmark=jsonObject.getString("bookmark");
                resultObj.put("status",1);
                resultObj.put(table,arrayList);
                resultObj.put("tableDataNum",tableDataNum);
                resultObj.put("bookmark",bookmark);
            }else{
                resultObj.put("status",0);
                resultObj.put("msg","查询到该表格"+table+"异常："+response.getResultMsg());
                log.error("查询到该表格"+table+"异常："+response.getResultMsg());
            }
        }
        returnResult.setStatus(1);
        returnResult.setMsg("查询表格数据成功");
        returnResult.setData(resultObj);
        return returnResult;
    }

    @Override
    public ReturnResult deleteTableDataBySyncDate(SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        if(syncRBCTableDataJobParam.getTables()==null || syncRBCTableDataJobParam.getTables().length==0){
            String tableName=config.getTableNames();
            String[] tableNames=tableName.split(",");
            syncRBCTableDataJobParam.setTables(tableNames);
        }
        if(StringUtils.isEmpty(syncRBCTableDataJobParam.getSyncDate())){
            syncRBCTableDataJobParam.setSyncDate(LocalDateUtil.dateToStr(LocalDate.now()));
        }
        if(StringUtils.isEmpty(syncRBCTableDataJobParam.getIndexName())){
            syncRBCTableDataJobParam.setIndexName("rows.SYNC_DATE");
        }
        for (String tableName : syncRBCTableDataJobParam.getTables()) {
            if(StringUtils.isNotEmpty(tableName)) {
                deleteTableDataByIndex(tableName,syncRBCTableDataJobParam.getIndexName(),syncRBCTableDataJobParam.getSyncDate());
            }
        }
        return new ReturnResult(1, "上链数据删除完成");
    }

    @Override
    public ReturnResult deleteTableDataByRowId(SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=new ReturnResult();
        if(syncRBCTableDataJobParam.getTables()==null || syncRBCTableDataJobParam.getTables().length==0){
            returnResult.setStatus(0);
            returnResult.setMsg("表格不能为空");
            return returnResult;
        }
        if(StringUtils.isEmpty(syncRBCTableDataJobParam.getRowId())){
            returnResult.setStatus(0);
            returnResult.setMsg("rowID不能为空");
            return returnResult;
        }
        try {
            String[] rowIdDateArray = syncRBCTableDataJobParam.getRowId().split(",");
            for (String tableName : syncRBCTableDataJobParam.getTables()) {
                if (StringUtils.isNotEmpty(tableName)) {
                    int deleteNum=0;
                    int deleteFailNum=0;
                    //遍历索引值
                    for (int i = 0; i < rowIdDateArray.length; i++) {
                        ApiResponse deleteApi = Consts.client.deleteRow(rowIdDateArray[i], tableName, config);
                        if (!"200".equals(deleteApi.getResultCode())) {
                            deleteFailNum += 1;
                            log.error("删除失败：deleteTableDataByRowId error:tableName={}, rowId={}", tableName, rowIdDateArray[i]);
                        } else {
                            deleteNum += 1;
                        }
                    }
                    log.info("删除成功：deleteTableDataByRowId info：tableName={}, deleteNum={}, deleteFailNum={}",tableName,deleteNum,deleteFailNum);
                }
            }
            returnResult.setStatus(1);
            returnResult.setMsg("上链数据根据rowID删除成功");
            return returnResult;
        }catch (Exception e){
            log.error("上链数据根据rowID删除失败：deleteTableDataByRowId error:tableName={}, rowID={}", syncRBCTableDataJobParam.getTables(),syncRBCTableDataJobParam.getRowId(), e);
            return new ReturnResult(0, "上链数据根据rowID删除失败！");
        }
    }

    public ReturnResult deleteTableDataByIndex(String tableName, String indexName,
                                               String syncDate){
        try {
            String[] syncDateArray = syncDate.split(",");
            //遍历索引值
            for (int i = 0; i < syncDateArray.length; i++) {
                JSONObject indexObj=new JSONObject();
                indexObj.put(indexName,syncDateArray[i]);
                deleteSingleTableDataByIndex(tableName,indexObj);
            }
        }catch (Exception e){
            log.error("上链数据删除失败：deleteTableDataBySyncDate error:tableName={}, indexName={}, syncDate={}", tableName, indexName, syncDate, e);
            return new ReturnResult(0, "上链数据删除失败！");
        }
        return new ReturnResult(1, "上链数据删除完成");
    }
    @Async("taskExecutor")
    public void deleteSingleTableDataByIndex(String tableName, JSONObject indexObj) throws Exception{
        ApiResponse response=Consts.client.selectPageByIndex(tableName,indexObj,"",Consts.WIDERANGE_ROW_NUM,config);
        //是否有下一页 true有 false无
        boolean hasNextPage = false;
        if(response==null){
            bizLogger.info("根据索引"+indexObj.toString()+",查询"+tableName+"数据为空");
        }else if("200".equals(response.getResultCode())) {
            JSONObject jsonObject = (JSONObject) response.getData();
            JSONArray arrayList = jsonObject.getJSONArray("results");
            int deleteNum=0;
            int deleteFailNum=0;
            for(int i=0;i<arrayList.size();i++){
                JSONObject object1 = arrayList.getJSONObject(i);
                String rowId=object1.getString("rowId");
                ApiResponse deleteApi=Consts.client.deleteRow(rowId,tableName,config);
                if(!"200".equals(deleteApi.getResultCode())){
                    deleteFailNum+=1;
                    log.error("删除失败：deleteSingleTableDataByIndex error:tableName={}, tableData={}",tableName,object1.toString());
                }else{
                    deleteNum+=1;
                }
            }

//            hasNextPage=nextPage(response,tableName,indexObj,Consts.WIDERANGE_ROW_NUM);
            JSONObject nextObj=nextPage(response,tableName,indexObj,Consts.WIDERANGE_ROW_NUM);
            hasNextPage=nextObj.getBooleanValue("hasNextPage");
//            String bookMark="";
            if(hasNextPage){
                jsonObject.getString("bookmark");
            }
            while (hasNextPage){
                ApiResponse response1=(ApiResponse) nextObj.get("apiResponse");
//                ApiResponse response1=Consts.client.selectPageByIndex(tableName,indexObj,bookMark,Consts.WIDERANGE_ROW_NUM,config);
                if(response1==null){
                    bizLogger.info("根据索引"+indexObj.toString()+",查询"+tableName+"数据为空");
                    hasNextPage = false;
                    break;
                }else if("200".equals(response1.getResultCode())) {
                    JSONObject jsonObject1 = (JSONObject) response1.getData();
                    JSONArray arrayList1 = jsonObject1.getJSONArray("results");
                    for(int i=0;i<arrayList1.size();i++){
                        JSONObject object2 = arrayList1.getJSONObject(i);
                        String rowId1=object2.getString("rowId");
                        ApiResponse deleteApi1=Consts.client.deleteRow(rowId1,tableName,config);
                        if(!"200".equals(deleteApi1.getResultCode())){
                            deleteFailNum+=1;
                            bizLogger.error("删除失败：deleteSingleTableDataByIndex error:tableName={}, tableData={}",tableName,object2.toString());
                        }else{
                            deleteNum+=1;
                        }
                    }
                    nextObj=nextPage(response,tableName,indexObj,Consts.WIDERANGE_ROW_NUM);
                    hasNextPage=nextObj.getBooleanValue("hasNextPage");
                    if(!hasNextPage){
//                        JSONObject object = (JSONObject) response1.getData();
//                        bookMark=object.getString("bookmark");
//                    }else{
                        break;
                    }
                }else{
                    bizLogger.info("查询"+tableName+"数据异常自动跳出循环："+response1.getResultCode()+",msg:"+response1.getResultMsg());
                    hasNextPage = false;
                    break;
                }
            }
            if(!hasNextPage){
                bizLogger.info("删除成功：deleteSingleTableDataByIndex info：tableName={}, deleteNum={}, deleteFailNum={}",tableName,deleteNum,deleteFailNum);
            }
        }else{
            bizLogger.error("根据索引查询"+tableName+"数据异常,code:"+response.getResultCode()+",msg:"+response.getResultMsg());
        }
    }
    private JSONObject nextPage(ApiResponse apiResponse,String tableName, JSONObject indexObj,Integer rowNum){
        JSONObject nextObj=new JSONObject();
        boolean hasNextPage = false;
        JSONObject jsonObject = (JSONObject) apiResponse.getData();
        JSONArray arrayList = jsonObject.getJSONArray("results");
        int dataSize = arrayList.size();
        if (dataSize < rowNum) {
            hasNextPage = false;
        } else if (dataSize == rowNum) {
            String bookMark=jsonObject.getString("bookmark");
            ApiResponse response=Consts.client.selectPageByIndex(tableName,indexObj,bookMark,rowNum,config);
            if(response==null){
                bizLogger.info("nextPage根据索引"+indexObj.toString()+",查询"+tableName+"数据为空");
                hasNextPage = false;
            }else if("200".equals(response.getResultCode())){
                JSONObject object = (JSONObject) response.getData();
                JSONArray listArray = object.getJSONArray("results");
                if(listArray.size()>0){
                    hasNextPage = true;
                    nextObj.put("apiResponse",response);
                }else{
                    bizLogger.info("nextPage根据索引"+indexObj.toString()+",查询"+tableName+"数据response："+response.toString());
                    hasNextPage = false;
                }
            }else {
                bizLogger.error("nextPage根据索引"+indexObj.toString()+",查询"+tableName+"数据异常,code:"+response.getResultCode()+",msg:"+response.getResultMsg());
                hasNextPage = false;
            }
        } else {
            hasNextPage = true;
        }
        nextObj.put("hasNextPage",hasNextPage);
        return nextObj;
    }
    private void queryBusinessRowCount(String tableName,int upChainType,String businessName){
        ApiResponse apiAppeal= Consts.client.getRowCount(tableName,config);
        if("200".equals(apiAppeal.getResultCode())){
            Integer appealCount=Integer.valueOf(apiAppeal.getData().toString());
            RealTimeBlockChain realTimeBlockChain=new RealTimeBlockChain();
            realTimeBlockChain.setUpchainType(upChainType);
            realTimeBlockChain.setUpchainNumber(Long.parseLong(appealCount.toString()));
            realTimeBlockChain.setCreateDate(LocalDateUtil.nowDate("yyyy-MM-dd HH:mm"));
            realTimeBlockChain.setCreateDateTime(LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_1));
            realTimeBlockChainMapper.insertBlockChainOnChain(realTimeBlockChain);
        }else{
            log.error("method:selectBusinessTypeNum,查询"+businessName+"数据的上链情况异常："+apiAppeal.getResultMsg());
        }
    }
    /**
     * 查询ADM_LICENSE,ADM_PENALTY,MOD_COMPANY,
     * T_CREDIT_REPAIR_APPLY,T_CREDIT_REPORT_APPLY,
     * T_RED_CREDIT_EVAL_TAX,CREDIT_COMMITMENT_2022,T_CREDIT_OBJECTION_APPEAL_APPLY
     * 这8个模型的所有数量
     * @return
     */
    @Override
    public ReturnResult selectAllNum() {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("查询8个模型记录总数成功");
        try {
            String tableName=config.getTableNames();
            String[] tableNames=tableName.split(",");
            Integer rowCount=0;
            for(String table:tableNames){
                ApiResponse apiResponse= Consts.client.getRowCount(table,config);
                if("200".equals(apiResponse.getResultCode())){
                    rowCount+=Integer.valueOf(apiResponse.getData().toString());
                }
            }
            returnResult.setData(rowCount);
            RealTimeBlockChain realTimeBlockChain=new RealTimeBlockChain();
            realTimeBlockChain.setUpchainType(3);
            realTimeBlockChain.setUpchainNumber(Long.parseLong(rowCount.toString()));
            realTimeBlockChain.setCreateDate(LocalDateUtil.nowDate("yyyy-MM-dd HH:mm"));
            realTimeBlockChain.setCreateDateTime(LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_1));
            realTimeBlockChainMapper.insertBlockChainOnChain(realTimeBlockChain);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("查询8个模型记录总数异常："+e.getMessage());
            log.error("method:selectAllNum,查询8个模型记录总数异常："+e.toString());
        }
        return returnResult;
    }

    private JSONObject queryEnterpriseInfoByUscc(JSONObject indexMod,String tableName){
        JSONObject resultObj=new JSONObject();
        ApiResponse response=Consts.client.selectPageByIndex(tableName,indexMod,"",Consts.WIDERANGE_ROW_NUM,config);
        if (response == null) {
            resultObj.put("status",0);
            resultObj.put("msg","未查询到通过统一社会信用代码查询"+tableName);
            log.error("未查询到通过统一社会信用代码查询"+tableName);
        } else if("200".equals(response.getResultCode())){
            //是否有下一页 true有 false无
            boolean hasNextPage = false;
            JSONObject jsonObject = (JSONObject) response.getData();
            JSONArray arrayList = jsonObject.getJSONArray("results");
            String bookmark="";
            if(arrayList.size()==Consts.WIDERANGE_ROW_NUM){
                hasNextPage=true;
                bookmark =jsonObject.getString("bookmark");
            }
            while (hasNextPage){
                ApiResponse response1=Consts.client.selectPageByIndex(tableName,indexMod,bookmark,Consts.WIDERANGE_ROW_NUM,config);
                if(response1==null){
                    log.error("未查询到通过统一社会信用代码查询"+tableName);
                    break;
                }else if("200".equals(response.getResultCode())){
                    JSONObject jsonObject1 = (JSONObject) response1.getData();
                    JSONArray arrayList1 = jsonObject1.getJSONArray("results");
                    if(arrayList1.size()>0){
                        arrayList.addAll(arrayList1);
                    }
                    if (arrayList1.size()==Consts.WIDERANGE_ROW_NUM){
                        hasNextPage=true;
                    }else if(arrayList1.size()<Consts.WIDERANGE_ROW_NUM){
                        hasNextPage=false;
                    }
                }else{
                    break;
                }
            }
            resultObj.put("status",1);
            resultObj.put("data",arrayList);
            resultObj.put("msg","通过统一社会信用代码查询"+tableName+"成功");
        }else{
            resultObj.put("status",0);
            resultObj.put("msg","通过统一社会信用代码查询"+tableName+"异常："+response.getResultMsg());
            log.error("通过统一社会信用代码查询"+tableName+"异常："+response.getResultMsg());
        }
        return resultObj;
    }
    private Long selectSubjectCreditByIndex(){
        JSONObject indexPersonalLegal=new JSONObject();
        indexPersonalLegal.put("rows.SUBJECT_TYPE","3");
        //按照主体类型查询自然人数据总记录
        String subTypeLegalPersonal=config.getSubTypeLegalPersonal();
        String[] subTypeLegalPersonals=subTypeLegalPersonal.split(",");
        Long legalPersonalRowCount=queryTableRowNum(subTypeLegalPersonals,indexPersonalLegal);
        return legalPersonalRowCount;
    }
    private Long queryTableRowNum(String[] tables,JSONObject index){
        Long rowCounts=0L;
        for(String table:tables){
            ApiResponse response=Consts.client.selectPageByIndex(table,index,"",Consts.WIDERANGE_ROW_NUM,config);
            if("200".equals(response.getResultCode())){
                JSONObject jsonObject = (JSONObject) response.getData();
                JSONArray arrayList = jsonObject.getJSONArray("results");
                rowCounts+=arrayList.size();
            }else{
                log.error("查询表格"+table+"中行记录总数异常："+response.getResultMsg());
                break;
            }
        }
        return rowCounts;
    }
}
