package com.rongzer.suzhou.scm.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rongzer.sdk.rbaas.entity.RbaasTableColumn;
import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.properties.CommonProperties;

import java.util.Map;

/**
 * @Description 苏州信用模型客户端
 * @Author tiancl
 * @Date 2022/10/13 15:01
 * @Version 1.0
 **/
public class SuzhouAgrClient {
    SdkClient sdkClient=new SdkClient();
    /**
     * @Description:获取区块链高度
     * @Author: tian.changlong
     * @Date: 2022/10/13 13:54
     * @return: ApiResponse
     **/
    public ApiResponse getBlockHeight(CommonProperties config){
        return sdkClient.getBlockHeight(config);
    }
    /**
     * @Description:获取节点数量
     * @Author: tian.changlong
     * @Date: 2022/10/17 14:30
     * @param config:
     * @return: int
     **/
    public int getNodes(CommonProperties config){
        return sdkClient.getNodes(config);
    }
    /**
     * @Description:获取通道中已经安装的合约数量
     * @Author: tian.changlong
     * @Date: 2022/10/17 14:32
     * @param config:
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse getContract(CommonProperties config){
        return sdkClient.getContract(config);
    }
    /**
     * @Description:获取数据历史信息
     * @Author: tian.changlong
     * @Date: 2022/10/17 15:29
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse getRowHistory(String rowId, String tableName, CommonProperties config){
        return sdkClient.getRowHistory(rowId,tableName,config);
    }
 /**
  * @Description:
  * @Author: tian.changlong
  * @Date: 2023/3/7 15:09
  * @param tableName: 
 * @param config:
  * @return: java.util.Map<java.lang.String,com.rongzer.sdk.rbaas.entity.RbaasTableColumn>
  **/
    public Map<String, RbaasTableColumn> getTableColumnMap(String tableName, CommonProperties config){
        return sdkClient.getTableColumnMap(tableName,config);
    }
    /**
     * @Description:查询区块信息
     * @Author: tian.changlong
     * @Date: 2022/10/20 16:19
     * @param config:
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse getBlockInfo( CommonProperties config){
        return sdkClient.getBlockInfo(config);
    }
    /**
     * @Description:根据区块hash查询区块信息
     * @Author: tian.changlong
     * @Date: 2022/10/21 9:47
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse getBlockInfoByBlockHash( CommonProperties config,String blockHash){
        return sdkClient.getBlockInfoByBlockHash(config,blockHash);
    }
    /**
     * @Description:根据主键查询数据
     * @Author: tian.changlong
     * @Date: 2022/10/17 14:33
     * @param tableName:
     * @param rowId:
     * @param config:
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse selectDataByPrimaryKey(String tableName,String rowId,CommonProperties config){
        return sdkClient.selectDataByPrimaryKey(tableName,rowId,config);
    }
    /**
     * @Description:数据上链
     * @Author: tian.changlong
     * @Date: 2022/10/17 15:25
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse uploadRow(String rowId, String tableName, JSONObject tableData, CommonProperties config, boolean isNew) throws Exception {
        ApiResponse apiResponse= sdkClient.uploadRow(rowId,tableName,tableData,config,isNew);
        return apiResponse;
    }
    /**
     * @Description:上链数据的删除
     * @Author: tian.changlong
     * @Date: 2023/8/14 16:59
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse deleteRow(String rowId, String tableName, CommonProperties config){
        ApiResponse apiResponse= sdkClient.deleteRow(rowId,tableName,config);
        return apiResponse;
    }
    /**
     * @Description:查询表格中行记录总数
     * @Author: tian.changlong
     * @Date: 2022/11/24 9:49
     * @param tableName:
     * @param config:
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse getRowCount(String tableName,CommonProperties config){
        return sdkClient.getRowCount(tableName,config);
    }
    /**
     * @Description:根据索引查询数据
     * @Author: tian.changlong
     * @Date: 2022/10/17 15:30
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse selectPageByIndex(String tableName, JSONObject indexObj, String bookMark, int size, CommonProperties config){
        return sdkClient.selectPageByIndex(tableName,indexObj,bookMark,size,config);
    }
    /**
     * @Description:根据排序查询数据
     * @Author: tian.changlong
     * @Date: 2023/12/21 15:03
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse selectPageBySort(String tableName, JSONArray sortArray, String bookMark, int size, CommonProperties config){
        return sdkClient.selectPageBySort(tableName,sortArray,bookMark,size,config);
    }
}
