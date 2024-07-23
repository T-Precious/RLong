package com.rongzer.suzhou.scm.consumer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rongzer.sdk.rbaas.entity.RbaasTableColumn;
import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.constant.Consts;
import com.rongzer.suzhou.scm.dao.LinkUpAbnormalDataMapper;
import com.rongzer.suzhou.scm.dao.TableDataMapper;
import com.rongzer.suzhou.scm.dao.TablesMapper;
import com.rongzer.suzhou.scm.pojo.LinkUpAbnormalData;
import com.rongzer.suzhou.scm.pojo.param.MqDtoParam;
import com.rongzer.suzhou.scm.properties.CommonProperties;
import com.rongzer.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2023/8/18 15:42
 * @Version 1.0
 **/
@Slf4j
@Component
public class ConsumerHandler {
    @Autowired
    private CommonProperties config;
    @Autowired
    private LinkUpAbnormalDataMapper linkUpAbnormalDataMapper;
    @Autowired
    private TablesMapper tablesMapper;
    @Autowired
    private TableDataMapper tableDataMapper;

    public static final Logger bizLogger= LoggerFactory.getLogger("bizSuzhouLogger");

    @RabbitListener(queues = "suzhou-first-queue",id = Consts.CONSUMERID,autoStartup = "false")
    public void handFirstQueueOne(MqDtoParam mqDtoParam){
        try {
            ApiResponse apiResponse=Consts.client.uploadRow(mqDtoParam.getRowId(),mqDtoParam.getTableName(),mqDtoParam.getTableData(),config,mqDtoParam.isCochainType());
            if("200".equals(apiResponse.getResultCode())) {
//                if("ADM_PENALTY".equals(mqDtoParam.getTableName()) || "ADM_LICENSE".equals(mqDtoParam.getTableName()) || "T_CREDIT_REVIEW_APPLY".equals(mqDtoParam.getTableName()) || "MOD_COMPANY".equals(mqDtoParam.getTableName()) || "CREDIT_COMMITMENT_2022".equals(mqDtoParam.getTableName()) || "T_TEST".equals(mqDtoParam.getTableName())){
                    ArrayList<String> tableFieldList = (ArrayList<String>) tablesMapper.selectColumnsByTableName(mqDtoParam.getTableName());
                    insertData(tableFieldList,mqDtoParam.getTableData(),mqDtoParam.getTableName());
//                }
            }else{
                bizLogger.info("数据上链响应结果："+apiResponse.toString());
                LinkUpAbnormalData link=new LinkUpAbnormalData();
                link.setRowId(mqDtoParam.getRowId());
                link.setTableName(mqDtoParam.getTableName());
                link.setTableData(mqDtoParam.getTableData().toJSONString());
                link.setTypeIsNew(mqDtoParam.isCochainType()+"");
                link.setExceptionMessage(apiResponse.toString());
                link.setCreateTime(LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_1));
                linkUpAbnormalDataMapper.insertLinkUpAbnormalData(link);
            }
        }catch (Exception e){
            bizLogger.error("消费者001,数据上链异常：tableName={},rowId={},tableData={},isNew={},e={}",mqDtoParam.getTableName(),mqDtoParam.getRowId(),mqDtoParam.getTableData(),mqDtoParam.isCochainType(),e.toString());
            LinkUpAbnormalData linkUpAbnormalData=new LinkUpAbnormalData();
            linkUpAbnormalData.setRowId(mqDtoParam.getRowId());
            linkUpAbnormalData.setTableName(mqDtoParam.getTableName());
            linkUpAbnormalData.setTableData(mqDtoParam.getTableData().toJSONString());
            linkUpAbnormalData.setTypeIsNew(mqDtoParam.isCochainType()+"");
            linkUpAbnormalData.setExceptionMessage(e.toString());
            linkUpAbnormalData.setCreateTime(LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_1));
            linkUpAbnormalDataMapper.insertLinkUpAbnormalData(linkUpAbnormalData);
        }
    }
    @RabbitListener(queues = "suzhou-first-queue",id = Consts.CONSUMERID2,autoStartup = "false")
    public void handFirstQueueTwo(MqDtoParam mqDtoParam){
        try {
//            log.info("消费者002");
//            log.info("消费者002:tableName={},rowId={},tableData={},isNew={}",mqDtoParam.getTableName(),mqDtoParam.getRowId(),mqDtoParam.getTableData(),mqDtoParam.isCochainType());
            ApiResponse apiResponse=Consts.client.uploadRow(mqDtoParam.getRowId(),mqDtoParam.getTableName(),mqDtoParam.getTableData(),config,mqDtoParam.isCochainType());
            if("200".equals(apiResponse.getResultCode())) {
//                if("ADM_PENALTY".equals(mqDtoParam.getTableName()) || "ADM_LICENSE".equals(mqDtoParam.getTableName()) || "T_CREDIT_REVIEW_APPLY".equals(mqDtoParam.getTableName()) || "MOD_COMPANY".equals(mqDtoParam.getTableName()) || "CREDIT_COMMITMENT_2022".equals(mqDtoParam.getTableName()) || "T_TEST".equals(mqDtoParam.getTableName())){
                    ArrayList<String> tableFieldList = (ArrayList<String>) tablesMapper.selectColumnsByTableName(mqDtoParam.getTableName());
                    insertData(tableFieldList,mqDtoParam.getTableData(),mqDtoParam.getTableName());
//                }
            }else{
                bizLogger.info("数据上链响应结果："+apiResponse.toString());
                LinkUpAbnormalData link=new LinkUpAbnormalData();
                link.setRowId(mqDtoParam.getRowId());
                link.setTableName(mqDtoParam.getTableName());
                link.setTableData(mqDtoParam.getTableData().toJSONString());
                link.setTypeIsNew(mqDtoParam.isCochainType()+"");
                link.setExceptionMessage(apiResponse.toString());
                link.setCreateTime(LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_1));
                linkUpAbnormalDataMapper.insertLinkUpAbnormalData(link);
            }
        }catch (Exception e){
            bizLogger.error("消费者002,数据上链异常：tableName={},rowId={},tableData={},isNew={},e={}",mqDtoParam.getTableName(),mqDtoParam.getRowId(),mqDtoParam.getTableData(),mqDtoParam.isCochainType(),e.toString());
            LinkUpAbnormalData linkUpAbnormalData2=new LinkUpAbnormalData();
            linkUpAbnormalData2.setRowId(mqDtoParam.getRowId());
            linkUpAbnormalData2.setTableName(mqDtoParam.getTableName());
            linkUpAbnormalData2.setTableData(mqDtoParam.getTableData().toJSONString());
            linkUpAbnormalData2.setTypeIsNew(mqDtoParam.isCochainType()+"");
            linkUpAbnormalData2.setExceptionMessage(e.toString());
            linkUpAbnormalData2.setCreateTime(LocalDateUtil.nowDate(LocalDateUtil.DATE_TIME_FORMAT_1));
            linkUpAbnormalDataMapper.insertLinkUpAbnormalData(linkUpAbnormalData2);
        }
    }
    public void insertData(List<String> tableFieldList, JSONObject object,
                              String tableName) {
        StringBuilder keyStr = new StringBuilder();
        for (String tableField : tableFieldList) {
            keyStr.append(",").append(tableField);
        }

//        List<String> valueList = new ArrayList<>();
//        for (int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject=object;
            //拼接值，例如('1','2')
            StringBuilder valueStrBuilder = new StringBuilder("(");
            for (String tableField : tableFieldList) {
                valueStrBuilder.append("'").append(jsonObject.getString(tableField)).append("',");
            }
            String valueStr = valueStrBuilder.toString();
            valueStr=valueStr.substring(0, valueStr.length() - 1) + ")";
//            valueList.add(valueStr.substring(0, valueStr.length() - 1) + ")");
//        }

//        if (valueList.size() > 0) {
            Map<String, Object> dataMap = new HashMap<>();

            dataMap.put("keyStr", keyStr.toString().substring(1));
            dataMap.put("valueStr", valueStr);
            dataMap.put("tableName", tableName);

            //新增或修改
            tableDataMapper.singleReplaceInto(dataMap);
            log.info("insertData:tableName={}", tableName);
//        }

    }
}
