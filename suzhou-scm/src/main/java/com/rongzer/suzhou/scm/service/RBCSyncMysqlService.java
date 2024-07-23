package com.rongzer.suzhou.scm.service;




import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.constant.Consts;
import com.rongzer.suzhou.scm.dao.TableDataMapper;
import com.rongzer.suzhou.scm.dao.TablesMapper;


import com.rongzer.suzhou.scm.properties.CommonProperties;
import com.rongzer.suzhou.scm.util.DataAnatomyUtil;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import com.rongzer.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description:从区块链同步表数据到mysql
 * @Author: tian.changlong
 * @Date: 2023/1/5 14:58
 **/
@Slf4j
@Service
public class RBCSyncMysqlService {

    @Autowired
    private TablesMapper tablesMapper;

    @Autowired
    private TableDataMapper tableDataMapper;

    @Autowired
    private CommonProperties config;


    /**
     * 根据索引同步苏州信用模型表数据
     * @param indexName
     * @param values
     */
    public void suzhouModelTableDataSyncByIndex(String modelName, String indexName, String values) {
//        List<String> tableNames = getSyncSuzhouModelTable();
        List<String> tableNames=Arrays.asList(config.getTableNames().split(","));
        for (String tableName : tableNames) {
            modelTableDataSyncByIndex(modelName, tableName, indexName, values);
        }
    }
    public void suzhouModelTableDataSyncByIndexAndTables(String modelName, String indexName, String values,String[] tables) {
        List<String> tableNames=new ArrayList<>();
        if(tables==null || tables.length==0){
//            tableNames= getSyncSuzhouModelTable();
            tableNames= Arrays.asList(config.getTableNames().split(","));
        }else{
            tableNames = Arrays.asList(tables);
        }
        for (String tableName : tableNames) {
            if(StringUtils.isNotEmpty(tableName)) {
                modelTableDataSyncByIndex(modelName, tableName, indexName, values);
            }
        }
    }
    /**
     * 根据索引和索引值列表 区块链同步到数据库
     *
     * @param modelName
     * @param tableName
     * @param indexName 索引名称
     * @param values    索引值，多个用英文逗号分隔
     * @return
     */
    public ReturnResult modelTableDataSyncByIndex(String modelName, String tableName, String indexName,
                                                  String values) {

        log.info("同步区块链数据到mysql:modelName={}, tableName={}, indexName={}, values={}", modelName, tableName, indexName, values);

        if (StringUtil.isEmpty(modelName) || StringUtil.isEmpty(tableName)) {
            log.error("modelTableDataSync error:modelName={}, tableName={}, msg={}", modelName, tableName, "模型名或表名为空");
            return new ReturnResult(0, "模型名或表名为空");
        }

        try {
            //判断表是否存在
            int isExistTable = tablesMapper.findTableCountByTableName(tableName);
            if (isExistTable == 0) {
                log.error("modelTableDataSync error:modelName={}, tableName={}, msg={}", modelName, tableName, "同步数据的表结构不存在");
                return new ReturnResult(0, "同步数据的表结构不存在");
            }
                if (StringUtil.isNotEmpty(indexName) && StringUtil.isNotEmpty(values)) {
                    String[] valueArray = values.split(",");
                    //遍历索引值，同步区块链数据到mysql
                    for (int i = 0; i < valueArray.length; i++) {
                        JSONObject indexObj=new JSONObject();
                        indexObj.put(indexName,valueArray[i]);
                        queryAndSaveModelData(tableName,indexObj);
//                        SpringContextUtil.getBean(RBCSyncMysqlService.class).queryAndSaveModelData(tableName,indexObj);
                    }
                } else {
                    queryAndSaveModelData(tableName,null);
//                    SpringContextUtil.getBean(RBCSyncMysqlService.class).queryAndSaveModelData(tableName,null);
                }

        } catch (Exception e) {
            log.error("modelTableDataSync error:modelName={}, tableName={}, indexName={}, values={}", modelName, tableName, indexName, values, e);
            return new ReturnResult(0, "同步失败！");
        }

        return new ReturnResult(1, "同步完成");
    }
    /**
     * 单张区块链模型表数据同步到数据库
     * @param queryTableName
     * @param indexObj
     * @throws Exception
     */
    @Async("taskExecutor")
    public void queryAndSaveModelData( String queryTableName, JSONObject indexObj) throws Exception{
        ArrayList<String> tableFieldList = (ArrayList<String>) tablesMapper.selectColumnsByTableName(queryTableName);
        ApiResponse apiResponse= Consts.client.selectPageByIndex(queryTableName,indexObj,"",Consts.ROW_NUM,config);
        //是否有下一页 true有 false无
        boolean hasNextPage = false;
        if(apiResponse==null){
            log.info("根据索引"+indexObj.toString()+",查询"+queryTableName+"数据为空");
        }else if("200".equals(apiResponse.getResultCode())) {
            insertRBCList(tableFieldList, DataAnatomyUtil.dataReturnSimpleAnatomy(apiResponse.getData()), queryTableName);
            JSONObject nextObj=nextPage(apiResponse,queryTableName,indexObj,Consts.ROW_NUM);
            hasNextPage=nextObj.getBooleanValue("hasNextPage");
//            String bookMark="";
//            if(hasNextPage){
//                JSONObject jsonObject = (JSONObject) apiResponse.getData();
//                 bookMark=jsonObject.getString("bookmark");
//            }
            while (hasNextPage){
                ApiResponse response= (ApiResponse) nextObj.get("apiResponse");
//                ApiResponse response=Consts.client.selectPageByIndex(queryTableName,indexObj,bookMark,Consts.PAGE_SIZE,config);
                if(response==null){
                    log.info("根据索引"+indexObj.toString()+",查询"+queryTableName+"数据为空");
                    break;
                }else if("200".equals(response.getResultCode())) {
                    insertRBCList(tableFieldList, DataAnatomyUtil.dataReturnSimpleAnatomy(response.getData()), queryTableName);
                    nextObj=nextPage(response,queryTableName,indexObj,Consts.ROW_NUM);
                    hasNextPage=nextObj.getBooleanValue("hasNextPage");
                    if(!hasNextPage){
//                        JSONObject object = (JSONObject) response.getData();
//                        bookMark=object.getString("bookmark");
//                    }else{
                        break;
                    }
                }else{
                    break;
                }
            }
        }else{
            log.error("根据索引查询"+queryTableName+"数据异常,code:"+apiResponse.getResultCode()+",msg:"+apiResponse.getResultMsg());
            hasNextPage = false;
        }
    }

    private JSONObject nextPage(ApiResponse apiResponse,String queryTableName, JSONObject indexObj,Integer rowNum){
        JSONObject nextObj=new JSONObject();
        boolean hasNextPage = false;
        JSONObject jsonObject = (JSONObject) apiResponse.getData();
        JSONArray arrayList = jsonObject.getJSONArray("results");
        int dataSize = arrayList.size();
        if (dataSize < rowNum) {
            hasNextPage = false;
        } else if (dataSize == rowNum) {
            String bookMark=jsonObject.getString("bookmark");
            ApiResponse response=Consts.client.selectPageByIndex(queryTableName,indexObj,bookMark,rowNum,config);
            if(response==null){
                log.info("根据索引"+indexObj.toString()+",查询"+queryTableName+"数据为空");
                hasNextPage = false;
            }else if("200".equals(response.getResultCode())){
                JSONObject object = (JSONObject) response.getData();
                JSONArray listArray = object.getJSONArray("results");
                if(listArray.size()>0){
                    hasNextPage = true;
                    nextObj.put("apiResponse",response);
                }else{
                    hasNextPage = false;
                }
            }else {
                log.error("根据索引"+indexObj.toString()+",查询"+queryTableName+"数据异常,code:"+response.getResultCode()+",msg:"+response.getResultMsg());
                hasNextPage = false;
            }
        } else {
            hasNextPage = true;
        }
        nextObj.put("hasNextPage",hasNextPage);
        return nextObj;
    }
    /**
     * 区块链查询出的列表插入mysql数据库
     * @param tableFieldList
     * @param tableName

     */
    public void insertRBCList(List<String> tableFieldList,JSONArray jsonArray,
                              String tableName) {
        StringBuilder keyStr = new StringBuilder();
        for (String tableField : tableFieldList) {
            keyStr.append(",").append(tableField);
        }

        List<String> valueList = new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            //拼接值，例如('1','2')
            StringBuilder valueStrBuilder = new StringBuilder("(");
            for (String tableField : tableFieldList) {
                valueStrBuilder.append("'").append(jsonObject.getString(tableField)).append("',");
            }
            String valueStr = valueStrBuilder.toString();
            valueList.add(valueStr.substring(0, valueStr.length() - 1) + ")");
        }

        if (valueList.size() > 0) {
            Map<String, Object> dataMap = new HashMap<>();

            dataMap.put("keyStr", keyStr.toString().substring(1));
            dataMap.put("valueList", valueList);
            dataMap.put("tableName", tableName);

            //新增或修改
            tableDataMapper.batchReplaceInto(dataMap);
            log.info("insertRBCListToMysql:tableName={}", tableName);
        }

    }



    /**
     * 苏州信用模型需要同步数据到Mysql表名称
     * @return
     */
    public List<String> getSyncSuzhouModelTable() {
        List<String> list = new ArrayList<>();
        //行政许可（变更）信息---已测试同步
        list.add("ADM_LICENSE");
        //行政处罚信息---已测试同步
        list.add("ADM_PENALTY");
        //查询对接方式表---已测试同步
        list.add("DATA_RECORD_ID_OBTAIN");
        //法人综合信用评价信息
        list.add("MOD_COMPANY");
//        //平台使用表---已测试同步
        list.add("T_APP_DAILY_INTERFACE_CALL");
//        //信用承诺信息---已测试同步
//        list.add("CREDIT_COMMITMENT_2022");
        list.add("CREDIT_COMMITMENT_2022");
//        //异议申诉申请表---已测试同步
        list.add("T_CREDIT_OBJECTION_APPEAL_APPLY");
        //信用修复申请表---已测试同步
        list.add("T_CREDIT_REPAIR_APPLY");
//        //信用修复记录表
        list.add("T_CREDIT_REPAIR_RECORD");
//        //信用修复状态表
        list.add("T_CREDIT_REPAIR_STATUS");
//        //信用报告申请表---已测试同步
        list.add("T_CREDIT_REPORT_APPLY");
//        //信用审查申请表
        list.add("T_CREDIT_REVIEW_APPLY");
//        //信用审查结果表
        list.add("T_CREDIT_REVIEW_APPLY_LIST");
        return list;
    }
}