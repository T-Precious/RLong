package com.rongzer.suzhou.scm.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Charsets;
import com.rongzer.suzhou.scm.constant.ExcelConstants;
import com.rongzer.suzhou.scm.dao.DepartmentChainNumMapper;
import com.rongzer.suzhou.scm.pojo.Column;
import com.rongzer.suzhou.scm.pojo.param.EnterpriseParam;
import com.rongzer.suzhou.scm.pojo.param.PageParam;
import com.rongzer.suzhou.scm.service.DepartmentChainNumService;
import com.rongzer.suzhou.scm.util.CalculateUtil;
import com.rongzer.suzhou.scm.util.DateUtil;
import com.rongzer.suzhou.scm.util.ExcelUtil;
import com.rongzer.suzhou.scm.util.ThreadPoolUtil;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import com.rongzer.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: pshe
 * @Date: 2023/2/7 9:51
 */
@Slf4j
@Service
public class DepartmentChainNumServiceImpl implements DepartmentChainNumService {

    @Autowired
    private ExcelUtil excelUtil;
    @Autowired
    private DepartmentChainNumMapper departmentChainNumMapper;
    @Override
    public ReturnResult countByDateType(String DateType) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("统计部门上链数据成功");
        try {
            String createDateStr="";
            if("day".equals(DateType)){
                createDateStr=DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,false,1);
            }else if("month".equals(DateType)){
                createDateStr=DateUtil.getFirstFewMonth(LocalDateUtil.nowDate("yyyy-MM"),"yyyy-MM",false,1);
            }else if("year".equals(DateType)){
                createDateStr=DateUtil.getFirstFewYear(LocalDateUtil.nowDate("yyyy"),"yyyy",false,1);
            }else{
                createDateStr=DateUtil.getFirstFewDays(LocalDateUtil.nowDate(LocalDateUtil.DATE_FORMAT_1),LocalDateUtil.DATE_FORMAT_1,false,1);
            }
            List<Map<String,Object>> jsonObjects = departmentChainNumMapper.countByDateType(createDateStr);
            returnResult.setData(jsonObjects);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("统计部门上链数据异常："+e.getMessage());
            log.error("统计部门上链数据异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult gatherAdm(String syncDate) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("success");
        try {
            if(StringUtils.isEmpty(syncDate)){
                LocalDate localDate = LocalDate.now();
                syncDate = localDate.plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            //查询当天有没有统计过，如果有先删除掉
//            int num = departmentChainNumMapper.hasGarherByCreateDate();
//            if(num>0){
//                departmentChainNumMapper.deleteHistoryGather();
//            }
            departmentChainNumMapper.gatherAdm(syncDate);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg(e.getMessage());
            log.error(e.toString());
        }
        return returnResult;
    }

    /**
     * 信用综合评价 xyzhpj MOD_COMPANY
     * 信用报告 xybg T_CREDIT_REPORT_APPLY
     * 信用审查 xysc  T_CREDIT_REVIEW_APPLY
     * 信用承诺 xycn  CREDIT_COMMITMENT_2022
     * 信用修复 xyxf  T_CREDIT_REPAIR_APPLY
     * 异议申诉 yyss  T_CREDIT_OBJECTION_APPEAL_APPLY
     * 行政许可 xzxk  ADM_LICENSE
     * 行政处罚 xzcf  ADM_PENALTY
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    @Override
    public ReturnResult getBusinessData(EnterpriseParam enterpriseParam, PageParam pageParam) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("success");
        try {
            Map<String,Object> map = new HashMap<>();
            //获取需要查询的业务
            String[] businesses = enterpriseParam.getBusinesses();
            CountDownLatch countDownLatch = new CountDownLatch(businesses.length);
            for(String business:businesses){
                ThreadPoolUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Object object = null;
                            switch (business){
                                case "xyzhpj":
                                    object = getModCompanyPage(enterpriseParam,pageParam);
                                    break;
                                case "xybg":
                                    object = getReportApplyPage(enterpriseParam,pageParam);
                                    break;
                                case "xysc":
                                    object = getReviewApplyPage(enterpriseParam,pageParam);
                                    break;
                                case "xycn":
                                    object = getCommitmentPage(enterpriseParam,pageParam);
                                    break;
                                case "xyxf":
                                    object = getRepairApplyPage(enterpriseParam,pageParam);
                                    break;
                                case "yyss":
                                    object = getObjectionAppealApplyPage(enterpriseParam,pageParam);
                                    break;
                                case "xzxk":
                                    object = getAdmLicensePage(enterpriseParam,pageParam);
                                    break;
                                case "xzcf":
                                    object = getAdmPenaltyPage(enterpriseParam,pageParam);
                                    break;
                                default:
                                    object = new JSONObject();
                            }
                            map.put(business,object);
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            countDownLatch.countDown();
                        }
                    }
                });
            }
            countDownLatch.await();
            returnResult.setData(map);
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg(e.getMessage());
            log.error(e.toString());
        }
        return returnResult;
    }

    @Override
    public void exportBusinessData(EnterpriseParam enterpriseParam, PageParam pageParam) {
        String[] businesses = enterpriseParam.getBusinesses();
        String business = businesses[0];
        List<Column> columns;
        switch (business){
            case "xyzhpj":
                columns = ExcelConstants.XYZHPJ;
                break;
            case "xybg":
                columns = ExcelConstants.XYBG;
                break;
            case "xysc":
                columns = ExcelConstants.XYSC;
                break;
            case "xycn":
                columns = ExcelConstants.XYCN;
                break;
            case "xyxf":
                columns = ExcelConstants.XYXF;
                break;
            case "yyss":
                columns = ExcelConstants.YYSS;
                break;
            case "xzxk":
                columns = ExcelConstants.XZXK;
                break;
            case "xzcf":
                columns = ExcelConstants.XZCF;
                break;
            default:
                columns = new ArrayList<>();
        }
        if(CollectionUtils.isEmpty(columns)){
            return;
        }
        List<List<String>> head = excelUtil.getHeader(columns);
        OutputStream outputStream =null;
        try{
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();
            outputStream = response.getOutputStream();
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();

            ReturnResult businessData = getBusinessData(enterpriseParam, pageParam);
            //创建Sheet
            WriteSheet writeSheet = EasyExcel.writerSheet(0, business).head(head)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
            excelWriter.write(null, writeSheet);
            Map<String,PageInfo> rootData = (Map<String,PageInfo>)businessData.getData();
            PageInfo pageInfo = rootData.get(business);
            int size = pageInfo.getSize();
            if(size > 0){
                List list = pageInfo.getList();
                //转换成可以写的格式
                List<List<Object>> lists = excelUtil.dataList(list, columns);
                //写数据
                excelWriter.write(lists, writeSheet);

//                int nextPage = pageInfo.getNextPage();
//                if(nextPage>0){
//                    pageParam.setCurrentPage(nextPage);
//                    businessData = getBusinessData(enterpriseParam, pageParam);
//                    rootData = (Map<String,PageInfo>)businessData.getData();
//                    pageInfo = rootData.get(business);
//                    size = pageInfo.getSize();
//                }
            }

            // 下载EXCEL
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding(Charsets.UTF_8.name());
            // 这里URLEncoder.encode可以防止浏览器端导出excel文件名中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(business, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());
            // 将文件名设置到头信息 js中要用
            response.setHeader("FileName", fileName + ExcelTypeEnum.XLSX.getValue());
            // 加入头信息白名单
            response.setHeader("Access-Control-Expose-Headers", "FileName");
            excelWriter.finish();
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 信用综合评价
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    public PageInfo<JSONObject> getModCompanyPage(EnterpriseParam enterpriseParam, PageParam pageParam){
        PageHelper.startPage(pageParam.getCurrentPage(),pageParam.getPageSize());
        List<JSONObject> jsonObjects = departmentChainNumMapper.getModCompanyPage(enterpriseParam);
        PageInfo<JSONObject> pageInfo = new PageInfo<>(jsonObjects);
        return pageInfo;
    }

    /**
     * 信用报告
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    public PageInfo<JSONObject> getReportApplyPage(EnterpriseParam enterpriseParam, PageParam pageParam){
        PageHelper.startPage(pageParam.getCurrentPage(),pageParam.getPageSize());
        List<JSONObject> jsonObjects = departmentChainNumMapper.getReportApplyPage(enterpriseParam);
        for(int i=0;i<jsonObjects.size();i++){
            JSONObject jsonObject=jsonObjects.get(i);
            String PARK_CREDIT_ID=jsonObject.getString("PARK_CREDIT_ID");
            String PROVINCES_CITY_ID=jsonObject.getString("PROVINCES_CITY_ID");
            String REPORT_LEVEL="";
            if(StringUtils.isNotEmpty(PARK_CREDIT_ID) && !"null".equals(PARK_CREDIT_ID)){
                REPORT_LEVEL="区级";
            }
            if(StringUtils.isNotEmpty(PROVINCES_CITY_ID) && !"null".equals(PROVINCES_CITY_ID)){
                REPORT_LEVEL="省级";
            }
            jsonObject.put("REPORT_LEVEL",REPORT_LEVEL);
        }
        PageInfo<JSONObject> pageInfo = new PageInfo<>(jsonObjects);
        return pageInfo;
    }

    /**
     * 信用审查
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    public PageInfo<JSONObject> getReviewApplyPage(EnterpriseParam enterpriseParam, PageParam pageParam){
        PageHelper.startPage(pageParam.getCurrentPage(),pageParam.getPageSize());
        List<JSONObject> jsonObjects = departmentChainNumMapper.getReviewApplyPage(enterpriseParam);
        for(int i=0;i<jsonObjects.size();i++){
            JSONObject jsonObject=jsonObjects.get(i);
            String PARK_ID=jsonObject.getString("PARK_ID");
            String PROVINCE_CITY_ID=jsonObject.getString("PROVINCE_CITY_ID");
            String REVIEW_LEVEL="";
            if(StringUtils.isNotEmpty(PARK_ID) && !"null".equals(PARK_ID)){
                REVIEW_LEVEL="区级";
            }
            if(StringUtils.isNotEmpty(PROVINCE_CITY_ID) && !"null".equals(PROVINCE_CITY_ID)){
                REVIEW_LEVEL="省级";
            }
            jsonObject.put("REVIEW_LEVEL",REVIEW_LEVEL);
            List<JSONObject> jsonObjectList=departmentChainNumMapper.getReviewApplyListPage(jsonObject.getString("ID"));
            jsonObject.put("REVIEW_NUM",jsonObjectList.size());
            int DISCREDIT_NUM=0;
            int HEAVIER_SERIOUS_DISCREDIT_NUM=0;
            for(int j=0;j<jsonObjectList.size();j++){
                JSONObject jsonObject1=jsonObjectList.get(j);
                String SERIOUS_DISCREDIT=jsonObject1.getString("SERIOUS_DISCREDIT");
                String HEAVIER_SERIOUS_DISCREDIT=jsonObject1.getString("HEAVIER_SERIOUS_DISCREDIT");
                String GENERAL_DISCREDIT=jsonObject1.getString("GENERAL_DISCREDIT");
                if(CalculateUtil.isNumeric(SERIOUS_DISCREDIT)){
                    DISCREDIT_NUM=DISCREDIT_NUM+Integer.parseInt(SERIOUS_DISCREDIT);
                }
                if(CalculateUtil.isNumeric(HEAVIER_SERIOUS_DISCREDIT)){
                    DISCREDIT_NUM=DISCREDIT_NUM+Integer.parseInt(HEAVIER_SERIOUS_DISCREDIT);
                    HEAVIER_SERIOUS_DISCREDIT_NUM=HEAVIER_SERIOUS_DISCREDIT_NUM+Integer.parseInt(HEAVIER_SERIOUS_DISCREDIT);
                }
                if(CalculateUtil.isNumeric(GENERAL_DISCREDIT)){
                    DISCREDIT_NUM=DISCREDIT_NUM+Integer.parseInt(GENERAL_DISCREDIT);
                }
            }
            jsonObject.put("DISCREDIT_NUM",DISCREDIT_NUM);
            jsonObject.put("HEAVIER_SERIOUS_DISCREDIT_NUM",HEAVIER_SERIOUS_DISCREDIT_NUM);
        }
        PageInfo<JSONObject> pageInfo = new PageInfo<>(jsonObjects);
        return pageInfo;
    }

    /**
     * 信用承诺
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    public PageInfo<JSONObject> getCommitmentPage(EnterpriseParam enterpriseParam, PageParam pageParam){
        PageHelper.startPage(pageParam.getCurrentPage(),pageParam.getPageSize());
        List<JSONObject> jsonObjects = departmentChainNumMapper.getCommitmentPage(enterpriseParam);
        PageInfo<JSONObject> pageInfo = new PageInfo<>(jsonObjects);
        return pageInfo;
    }

    /**
     * 信用修复
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    public PageInfo<JSONObject> getRepairApplyPage(EnterpriseParam enterpriseParam, PageParam pageParam){
        PageHelper.startPage(pageParam.getCurrentPage(),pageParam.getPageSize());
        List<JSONObject> jsonObjects = departmentChainNumMapper.getRepairApplyPage(enterpriseParam);
        PageInfo<JSONObject> pageInfo = new PageInfo<>(jsonObjects);
        return pageInfo;
    }


    /**
     * 异议申诉
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    public PageInfo<JSONObject> getObjectionAppealApplyPage(EnterpriseParam enterpriseParam, PageParam pageParam){
        PageHelper.startPage(pageParam.getCurrentPage(),pageParam.getPageSize());
        List<JSONObject> jsonObjects = departmentChainNumMapper.getObjectionAppealApplyPage(enterpriseParam);
        PageInfo<JSONObject> pageInfo = new PageInfo<>(jsonObjects);
        return pageInfo;
    }


    /**
     * 行政许可
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    public PageInfo<JSONObject> getAdmLicensePage(EnterpriseParam enterpriseParam, PageParam pageParam){
        PageHelper.startPage(pageParam.getCurrentPage(),pageParam.getPageSize());
        List<JSONObject> jsonObjects = departmentChainNumMapper.getAdmLicensePage(enterpriseParam);
        for(JSONObject jsonObject:jsonObjects){
            String CERT_NUM=jsonObject.getString("CERT_NUM");
            if(StringUtils.isEmpty(CERT_NUM) || "null".equals(CERT_NUM)){
                jsonObject.put("CERT_NUM",jsonObject.getString("USCC"));
            }
            if(StringUtils.isNotEmpty(jsonObject.getString("SOURCE_RECORD_ID"))) {
                JSONObject jsonObject1 = departmentChainNumMapper.getDataRecordByID(jsonObject.getString("SOURCE_RECORD_ID"));
                if (jsonObject1 != null) {
                    jsonObject.put("DOCKING_METHOD", jsonObject1.getString("DOCKING_METHOD"));
                }
            }
        }
        PageInfo<JSONObject> pageInfo = new PageInfo<>(jsonObjects);
        return pageInfo;
    }


    /**
     * 行政处罚
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    public PageInfo<JSONObject> getAdmPenaltyPage(EnterpriseParam enterpriseParam, PageParam pageParam){
        PageHelper.startPage(pageParam.getCurrentPage(),pageParam.getPageSize());
        List<JSONObject> jsonObjects = departmentChainNumMapper.getAdmPenaltyPage(enterpriseParam);
        for(JSONObject jsonObject:jsonObjects){
            String CERT_NUM=jsonObject.getString("CERT_NUM");
            if(StringUtils.isEmpty(CERT_NUM) || "null".equals(CERT_NUM)){
                jsonObject.put("CERT_NUM",jsonObject.getString("USCC"));
            }
            if(StringUtils.isNotEmpty(jsonObject.getString("SOURCE_RECORD_ID"))) {
                JSONObject jsonObject1 = departmentChainNumMapper.getDataRecordByID(jsonObject.getString("SOURCE_RECORD_ID"));
                if (jsonObject1 != null) {
                    jsonObject.put("DOCKING_METHOD", jsonObject1.getString("DOCKING_METHOD"));
                }
            }
        }
        PageInfo<JSONObject> pageInfo = new PageInfo<>(jsonObjects);
        return pageInfo;
    }

}
