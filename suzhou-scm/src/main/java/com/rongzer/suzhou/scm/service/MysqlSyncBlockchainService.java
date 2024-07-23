package com.rongzer.suzhou.scm.service;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.sdk.rbaas.entity.RbaasTableColumn;
import com.rongzer.suzhou.scm.constant.Consts;
import com.rongzer.suzhou.scm.dao.TablesMapper;
import com.rongzer.suzhou.scm.pojo.param.SyncRBCTableDataJobParam;
import com.rongzer.suzhou.scm.properties.CommonProperties;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import com.rongzer.util.LocalDateUtil;
import com.rongzer.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description 从mysql同步数据到区块链
 * @Author THINKPAD
 * @Date 2023/3/7 15:00
 * @Version 1.0
 **/
@Slf4j
@Service
public class MysqlSyncBlockchainService {
    @Autowired
    private TablesMapper tablesMapper;
    @Autowired
    private CommonProperties config;

    public void suzhouMysqlTableDataSyncAndRbc(String modelName, String indexName, String values,String[] tables) {
        if(tables==null || tables.length==0){
            log.error("同步数据库数据到区块链，表格名称不能为空！");
        }else{
            List<String> tableNames = Arrays.asList(tables);
            for (String tableName : tableNames) {
                if(StringUtils.isNotEmpty(tableName)) {
                    mysqlTableDataSyncByIndex(modelName, tableName, indexName, values);
                }
            }
        }

    }
    /**
     * 根据索引和索引值列表 数据库同步到区块链
     *
     * @param modelName
     * @param tableName
     * @param indexName 索引名称
     * @param values    索引值，多个用英文逗号分隔
     * @return
     */
    public ReturnResult mysqlTableDataSyncByIndex(String modelName, String tableName, String indexName,
                                                  String values) {

        log.info("同步数据库数据到区块链:modelName={}, tableName={}, indexName={}, values={}", modelName, tableName, indexName, values);

        if (StringUtil.isEmpty(modelName) || StringUtil.isEmpty(tableName)) {
            log.error("mysqlTableDataSyncByIndex error:modelName={}, tableName={}, msg={}", modelName, tableName, "模型名或表名为空");
            return new ReturnResult(0, "模型名或表名为空");
        }

        try {
            //判断表是否存在
            Map<String, RbaasTableColumn> tableColumnMap=Consts.client.getTableColumnMap(tableName,config);
            System.out.println(tableColumnMap.toString());
            if (tableColumnMap == null) {
                log.error("mysqlTableDataSyncByIndex error:modelName={}, tableName={}, msg={}", modelName, tableName, "同步数据的表结构不存在");
                return new ReturnResult(0, "同步数据的表结构不存在");
            }
            Set<String> stringSet=tableColumnMap.keySet();
            if (StringUtil.isNotEmpty(indexName) && StringUtil.isNotEmpty(values)) {
                String[] valueArray = values.split(",");
                List<String> primaryValues= Arrays.asList(valueArray);
                queryAndSaveMysqlData(tableName,indexName,primaryValues,stringSet);
            } else {
                queryAndSaveMysqlData(tableName,indexName,null,stringSet);
            }

        } catch (Exception e) {
            log.error("queryAndSaveMysqlData error:modelName={}, tableName={}, indexName={}, values={}", modelName, tableName, indexName, values, e);
            return new ReturnResult(0, "同步失败！");
        }

        return new ReturnResult(1, "同步完成");
    }
    /**
     * 单张区块链模型表数据同步到数据库
     * @param queryTableName
     * @throws Exception
     */
    @Async("taskExecutor")
    public void queryAndSaveMysqlData( String queryTableName,String indexName, List<String> primaryValues,Set<String> stringSet) throws Exception{
        Integer page=1;
        Integer pageSize=Consts.PAGE_SIZE;
        List<JSONObject> objectList=tablesMapper.selectTableDataByIndex(queryTableName,indexName,primaryValues,(page-1)*pageSize,pageSize);
        //是否有下一页 true有 false无
        boolean hasNextPage = false;
        if(objectList==null || objectList.size()==0){
            log.info("根据索引"+primaryValues.toString()+",查询"+queryTableName+"数据为空");
        }else {
            insertBlockchainList(stringSet, objectList, queryTableName);
            hasNextPage=nextPage(objectList,queryTableName,indexName,primaryValues,page,pageSize);
            while (hasNextPage){
                page=page+1;
                List<JSONObject> jsonObjectList=tablesMapper.selectTableDataByIndex(queryTableName,indexName,primaryValues,(page-1)*pageSize,pageSize);
                if(jsonObjectList==null || jsonObjectList.size()==0){
                    log.info("根据索引"+primaryValues.toString()+",查询"+queryTableName+"数据为空");
                    break;
                }else {
                    insertBlockchainList(stringSet, jsonObjectList, queryTableName);
                    hasNextPage=nextPage(jsonObjectList,queryTableName,indexName,primaryValues,page,pageSize);
                }
            }
        }
    }
    private boolean nextPage(List<JSONObject> objectList,String queryTableName,String indexName, List<String> primaryValues,Integer page,Integer pageSize){
        boolean hasNextPage = false;
        Integer pageNum=page+1;
        int dataSize = objectList.size();
        if (dataSize < Consts.PAGE_SIZE) {
            hasNextPage = false;
        } else if (dataSize == Consts.PAGE_SIZE) {
            List<JSONObject> list=tablesMapper.selectTableDataByIndex(queryTableName,indexName,primaryValues,(pageNum-1)*pageSize,pageSize);
            if(list==null || list.size()==0){
                log.info("根据索引"+primaryValues.toString()+",查询"+queryTableName+"数据为空");
                hasNextPage = false;
            }else{
                if(list.size()>0){
                    hasNextPage = true;
                }else{
                    hasNextPage = false;
                }
            }
        } else {
            hasNextPage = true;
        }
        return hasNextPage;
    }
    /**
     * 区块链查询出的列表插入mysql数据库
     * @param stringSet
     * @param tableName

     */
    public void insertBlockchainList(Set<String> stringSet, List<JSONObject> objectList,
                              String tableName) {
        for (int i=0;i<objectList.size();i++) {
            JSONObject object=objectList.get(i);
            JSONObject tableData=new JSONObject();
            for (String setStr:stringSet){
                tableData.put(setStr,object.get(setStr));
            }
            String rowId=object.getString("ROWID");
            if(StringUtils.isEmpty(rowId)){
                rowId="TT"+ LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_2)+ RandomStringUtils.randomAlphanumeric(3);
            }
            try {
                Consts.client.uploadRow(rowId,tableName,tableData,config,true);
            }catch (Exception e){
                log.error("上链异常：rowId={},tableName={},tableData={},e={}",rowId,tableName,tableData,e.toString());
            }
        }
    }
    public ReturnResult deleteMysqlTableDataByIndex(SyncRBCTableDataJobParam syncRBCTableDataJobParam){
        String[] tables=syncRBCTableDataJobParam.getTables();
        String indexName=syncRBCTableDataJobParam.getIndexName();
        String values=syncRBCTableDataJobParam.getSyncDate();
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        returnResult.setMsg("删除数据成功");
        JSONObject resultObj=new JSONObject();
        if (StringUtil.isEmpty(indexName) || StringUtil.isEmpty(values)) {
            log.error("索引名称或者索引值不能为空");
            returnResult.setStatus(0);
            returnResult.setMsg("索引名称或者索引值不能为空");
        }else {
            if (tables == null || tables.length == 0) {
                log.error("表格名称不能为空！");
                returnResult.setStatus(0);
                returnResult.setMsg("表格名称不能为空");
            } else {
                List<String> tableNames = Arrays.asList(tables);
                String[] valueArray = values.split(",");
                List<String> primaryValues = Arrays.asList(valueArray);
                for (String tableName : tableNames) {
                    if (StringUtils.isNotEmpty(tableName)) {
                        int deleteNum = 0;
                        deleteNum = tablesMapper.deleteTableDataByIndex(tableName, indexName, primaryValues);
                        resultObj.put(tableName,deleteNum);
                    }
                }
                returnResult.setData(resultObj);
            }
        }
        return returnResult;
    }
}
