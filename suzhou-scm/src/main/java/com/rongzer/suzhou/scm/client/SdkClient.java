package com.rongzer.suzhou.scm.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rongzer.rdp.core.tool.utils.StringUtil;
import com.rongzer.sdk.rbaas.api.ChainCodeManager;
import com.rongzer.sdk.rbaas.api.RbcChannelManager;
import com.rongzer.sdk.rbaas.api.RbcModelManager;
import com.rongzer.sdk.rbaas.config.ChannelClient;
import com.rongzer.sdk.rbaas.entity.BlockInfoApiEntity;
import com.rongzer.sdk.rbaas.entity.ChainCodeApiEntity;
import com.rongzer.sdk.rbaas.entity.RbaasTableColumn;
import com.rongzer.sdk.rbaas.entity.RowApiEntity;
import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.properties.CommonProperties;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Peer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: tiancl
 * Date: 2022/10/13
 * Description:
 */
public class SdkClient {

    Logger log = LoggerFactory.getLogger(SdkClient.class);
    /**
     * @Description:获取区块链高度
     * @Author: tian.changlong
     * @Date: 2022/10/13 13:54
     * @return: java.lang.String
     **/
   public ApiResponse getBlockHeight(CommonProperties config){
       ApiResponse channelHeight=null;
       try {
           BlockInfoApiEntity blockInfoApiEntity=new BlockInfoApiEntity();
           blockInfoApiEntity.setJsonConfig(config.getConnectCfg());
           blockInfoApiEntity.setSecurityCert(config.getWalletDir());
           blockInfoApiEntity.setChannelName(config.getChannelName());
           channelHeight= RbcChannelManager.getChannelHeight(blockInfoApiEntity);
       } catch (Exception e) {
           log.error("获取区块链高度异常",e);
       }
       return channelHeight;
   }
   /**
    * @Description:获取节点数量
    * @Author: tian.changlong
    * @Date: 2022/10/13 14:10
    * @param config:
    * @return: java.lang.String
    **/
   public int getNodes(CommonProperties config){
       ChainCodeApiEntity chainCodeApiEntity=new ChainCodeApiEntity();
       chainCodeApiEntity.setJsonConfig(config.getConnectCfg());
       chainCodeApiEntity.setSecurityCert(config.getWalletDir());
       chainCodeApiEntity.setChannelName(config.getChannelName());
       ChannelClient channelClient=new ChannelClient(null,chainCodeApiEntity.getJsonConfig(),chainCodeApiEntity.getSecurityCert(),chainCodeApiEntity.getChannelName(),null);
       Channel channel=channelClient.getChannel();
       Collection<Peer> peers=channel.getPeers();
       return peers.size();
   }
   /**
    * @Description:获取通道中已经安装的合约数量
    * @Author: tian.changlong
    * @Date: 2022/10/13 14:22
    * @param config:
    * @return: java.lang.String
    **/
   public ApiResponse getContract(CommonProperties config){
       ApiResponse apiResponse=null;
       try {
           ChainCodeApiEntity chainCodeApiEntity=new ChainCodeApiEntity();
           chainCodeApiEntity.setJsonConfig(config.getConnectCfg());
           chainCodeApiEntity.setSecurityCert(config.getWalletDir());
           chainCodeApiEntity.setChannelName(config.getChannelName());
           chainCodeApiEntity.setKeyFilePath(config.getKeyFilePath());
           chainCodeApiEntity.setCertFilePath(config.getCertFilePath());
           apiResponse= ChainCodeManager.getInstalledChainCodeCount(chainCodeApiEntity);
       } catch (Exception e) {
           log.error("获取通道中已经安装的合约数量异常",e);
       }
       return apiResponse;

   }
    /**
     * 获取数据历史信息
     * @param rowId
     * @param tableName
     * @return
     * @throws Exception
     */
    public ApiResponse getRowHistory(String rowId, String tableName, CommonProperties config){
        ApiResponse apiResponse=null;
        try {
            RowApiEntity historyRequest = new RowApiEntity();
            historyRequest.setJsonConfig(config.getConnectCfg());
            historyRequest.setSecurityCert(config.getWalletDir());
            historyRequest.setChaincodeName(config.getChainCodeName());
            historyRequest.setChaincodeVersion(config.getChainCodeVersion());
            historyRequest.setChannelName(config.getChannelName());
            historyRequest.setModelName(config.getModelName());
            historyRequest.setTableName(tableName);
            historyRequest.setRowId(rowId);
            apiResponse= RbcModelManager.getRowHistory(historyRequest);
        } catch (Exception e) {
            log.error("获取数据历史信息异常",e);
        }
        return apiResponse;
    }
    /**
     * 获取表格的字段名称
     * @param tableName
     * @return
     * @throws Exception
     */
    public Map<String, RbaasTableColumn> getTableColumnMap(String tableName, CommonProperties config){
        Map<String, RbaasTableColumn> tableColumnMap= null;
        try {
            RowApiEntity columRequest = new RowApiEntity();
            columRequest.setJsonConfig(config.getConnectCfg());
            columRequest.setSecurityCert(config.getWalletDir());
            columRequest.setChaincodeName(config.getChainCodeName());
            columRequest.setChaincodeVersion(config.getChainCodeVersion());
            columRequest.setChannelName(config.getChannelName());
            columRequest.setModelName(config.getModelName());
            columRequest.setTableName(tableName);
            tableColumnMap= RbcModelManager.getTableColumn(columRequest);
        } catch (Exception e) {
            log.error("获取表格的字段名称信息异常",e);
        }
        return tableColumnMap;
    }
    /**
     * @Description:查询区块信息
     * @Author: tian.changlong
     * @Date: 2022/10/20 16:16

     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse getBlockInfo( CommonProperties config){
        ApiResponse res1=null;
        try {
            BlockInfoApiEntity blockInfoApiEntity=new BlockInfoApiEntity();
            blockInfoApiEntity.setJsonConfig(config.getConnectCfg());
            blockInfoApiEntity.setSecurityCert(config.getWalletDir());
            blockInfoApiEntity.setChannelName(config.getChannelName());
            res1 = RbcChannelManager.getBlockInfo(blockInfoApiEntity);
        } catch (Exception e) {
            log.error("查询区块信息异常",e);
        }
        return res1;
    }
    /**
     * @Description:根据区块hash查询区块信息
     * @Author: tian.changlong
     * @Date: 2022/10/20 16:16

     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse getBlockInfoByBlockHash( CommonProperties config,String blockHash){
        ApiResponse res1=null;
        try {
            BlockInfoApiEntity blockInfoApiEntity=new BlockInfoApiEntity();
            blockInfoApiEntity.setJsonConfig(config.getConnectCfg());
            blockInfoApiEntity.setSecurityCert(config.getWalletDir());
            blockInfoApiEntity.setChannelName(config.getChannelName());
            blockInfoApiEntity.setBlockHash(blockHash);
            res1 = RbcChannelManager.getBlockInfo(blockInfoApiEntity);
        } catch (Exception e) {
            log.error("根据区块hash查询区块信息异常",e);
        }
        return res1;
    }
   /**
    * @Description:数据上链
    * @Author: tian.changlong
    * @Date: 2022/10/13 14:43
    * @return: com.rongzer.sdk.rbaas.response.ApiResponse
    **/
   public ApiResponse uploadRow(String rowId, String tableName, JSONObject tableData,CommonProperties config,boolean isNew) throws Exception {
       RowApiEntity rowApiEntity=new RowApiEntity();
       ApiResponse apiResponse=null;
//       try {
           rowApiEntity.setChaincodeName(config.getChainCodeName());
           rowApiEntity.setChaincodeVersion(config.getChainCodeVersion());
           rowApiEntity.setJsonConfig(config.getConnectCfg());
           rowApiEntity.setSecurityCert(config.getWalletDir());
           rowApiEntity.setChannelName(config.getChannelName());
           rowApiEntity.setModelName(config.getModelName());
           rowApiEntity.setTableName(tableName);
           rowApiEntity.setRowId(rowId);
           rowApiEntity.setTrTdValue(tableData);
           rowApiEntity.setIsEncrypt(false);
           if (isNew){
               apiResponse= RbcModelManager.createRow(rowApiEntity,null);
           }else {
               apiResponse = RbcModelManager.updateRow(rowApiEntity);
           }
//       } catch (Exception e) {
//           log.error("数据上链异常",e);
//       }
       return apiResponse;
   }
   /**
    * @Description:上链数据的删除
    * @Author: tian.changlong
    * @Date: 2023/8/14 16:57
    * @return: com.rongzer.sdk.rbaas.response.ApiResponse
    **/
    public ApiResponse deleteRow(String rowId, String tableName,CommonProperties config){
        RowApiEntity rowApiEntity=new RowApiEntity();
        ApiResponse apiResponse=null;
        try {
            rowApiEntity.setChaincodeName(config.getChainCodeName());
            rowApiEntity.setChaincodeVersion(config.getChainCodeVersion());
            rowApiEntity.setJsonConfig(config.getConnectCfg());
            rowApiEntity.setSecurityCert(config.getWalletDir());
            rowApiEntity.setChannelName(config.getChannelName());
            rowApiEntity.setModelName(config.getModelName());
            rowApiEntity.setTableName(tableName);
            rowApiEntity.setRowId(rowId);
            apiResponse = RbcModelManager.deleteRow(rowApiEntity);
        } catch (Exception e) {
            log.error("上链数据的删除异常",e);
        }
        return apiResponse;
    }
    /**
     * @Description:查询表格中行记录总数
     * @Author: tian.changlong
     * @Date: 2022/11/24 9:38
     * @param tableName:
     * @param config:
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse getRowCount(String tableName,CommonProperties config){
        RowApiEntity rowApiEntity=new RowApiEntity();
        ApiResponse apiResponse=null;
        try {
            rowApiEntity.setChaincodeName(config.getChainCodeName());
            rowApiEntity.setChaincodeVersion(config.getChainCodeVersion());
            rowApiEntity.setJsonConfig(config.getConnectCfg());
            rowApiEntity.setSecurityCert(config.getWalletDir());
            rowApiEntity.setChannelName(config.getChannelName());
            rowApiEntity.setModelName(config.getModelName());
            rowApiEntity.setTableName(tableName);
            apiResponse=RbcModelManager.getRowCount(rowApiEntity);
        } catch (Exception e) {
            log.error("查询表格中行记录总数异常",e);
        }
        return apiResponse;
    }
   /**
    * @Description:根据主键查询数据
    * @Author: tian.changlong
    * @Date: 2022/10/17 14:21
    * @return: com.rongzer.sdk.rbaas.response.ApiResponse
    **/
   public ApiResponse selectDataByPrimaryKey(String tableName,String rowId,CommonProperties config){
       ApiResponse apiResponse=null;
       try {
           RowApiEntity rowApiEntity=new RowApiEntity();
           rowApiEntity.setJsonConfig(config.getConnectCfg());
           rowApiEntity.setSecurityCert(config.getWalletDir());
           rowApiEntity.setChaincodeName(config.getChainCodeName());
           rowApiEntity.setChaincodeVersion(config.getChainCodeVersion());
           rowApiEntity.setChannelName(config.getChannelName());
           rowApiEntity.setModelName(config.getModelName());
           rowApiEntity.setTableName(tableName);
           rowApiEntity.setPageSize(100);
           rowApiEntity.setBookMark("");
           JSONObject perfixJson=new JSONObject();
           perfixJson.put("rowId",rowId);
           rowApiEntity.setPrefix(perfixJson.toJSONString());
           apiResponse= RbcModelManager.selectAdhocRowPage(rowApiEntity);
       } catch (Exception e) {
           log.error("根据主键查询数据异常",e);
       }
       return apiResponse;
   }
   /**
    * @Description:根据索引查询数据
    * @Author: tian.changlong
    * @Date: 2022/10/17 15:00
    * @return: com.rongzer.sdk.rbaas.response.ApiResponse
    **/
    public ApiResponse selectPageByIndex(String tableName, JSONObject indexObj, String bookMark, int size, CommonProperties config){
        ApiResponse apiResponse=null;
        try {
            RowApiEntity request = new RowApiEntity();
            request.setJsonConfig(config.getConnectCfg());
            request.setSecurityCert(config.getWalletDir());
            request.setChaincodeName(config.getChainCodeName());
            request.setChaincodeVersion(config.getChainCodeVersion());
            request.setChannelName(config.getChannelName());
            request.setModelName(config.getModelName());
            request.setTableName(tableName);
            request.setPageSize(size);
            if (StringUtil.isBlank(bookMark)){
                request.setBookMark("");
            }else{
                request.setBookMark(bookMark);
            }
            if(indexObj!=null){
                request.setPrefix(indexObj.toJSONString());
            }
            apiResponse= RbcModelManager.selectAdhocRowPage(request);
        } catch (Exception e) {
            log.error("根据索引查询数据异常",e);
        }
        return apiResponse;
    }
    /**
     * @Description:根据排序查询数据
     * @Author: tian.changlong
     * @Date: 2022/10/17 15:00
     * @return: com.rongzer.sdk.rbaas.response.ApiResponse
     **/
    public ApiResponse selectPageBySort(String tableName, JSONArray sortArray, String bookMark, int size, CommonProperties config){
        ApiResponse apiResponse=null;
        try {
            RowApiEntity request = new RowApiEntity();
            request.setJsonConfig(config.getConnectCfg());
            request.setSecurityCert(config.getWalletDir());
            request.setChaincodeName(config.getChainCodeName());
            request.setChaincodeVersion(config.getChainCodeVersion());
            request.setChannelName(config.getChannelName());
            request.setModelName(config.getModelName());
            request.setTableName(tableName);
            request.setPageSize(size);
            if (StringUtil.isBlank(bookMark)){
                request.setBookMark("");
            }else{
                request.setBookMark(bookMark);
            }
            if(sortArray!=null){
                request.setSort(JSON.toJSONString(sortArray));
            }
            apiResponse= RbcModelManager.selectAdhocRowPage(request);
        } catch (Exception e) {
            log.error("根据索引查询数据异常",e);
        }
        return apiResponse;
    }
}