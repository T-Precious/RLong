package com.rongzer.suzhou.scm.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rongzer.sdk.rbaas.entity.BlockChainInfo;
import com.rongzer.sdk.rbaas.entity.TxInfo;

import java.util.List;

/**
 * @Description 数据解剖工具类
 * @Author tiancl
 * @Date 2022/10/19 15:07
 * @Version 1.0
 **/
public class DataAnatomyUtil {
    /**
     * @Description:方法返回数据解剖
     * @Author: tian.changlong
     * @Date: 2022/10/19 15:09
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public static JSONObject dataReturnAnatomy(Object data){
        JSONObject jsonObject = (JSONObject) data;
        JSONArray arrayList = jsonObject.getJSONArray("results");
        JSONObject object = arrayList.getJSONObject(0);
        JSONObject resultObj = object.getJSONObject("rows");
        return resultObj;
    }
    /**
     * @Description:分页方法返回数据解剖
     * @Author: tian.changlong
     * @Date: 2022/10/20 15:43
     * @param data:
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public static JSONArray dataReturnSimpleAnatomy(Object data){
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject = (JSONObject) data;
        JSONArray arrayList = jsonObject.getJSONArray("results");
        for (int i=0;i<arrayList.size();i++){
            JSONObject object = arrayList.getJSONObject(i);
            JSONObject rowObj = object.getJSONObject("rows");
            rowObj.put("ROWID",object.getString("rowId"));
            jsonArray.add(rowObj);
        }
        return jsonArray;
    }
    /**
     * @Description:分页方法返回数据解剖
     * @Author: tian.changlong
     * @Date: 2022/10/20 15:43
     * @param data:
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public static JSONObject pageDataReturnAnatomy(Object data,boolean hasNextPage){
        JSONObject resultObj=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject = (JSONObject) data;
        JSONArray arrayList = jsonObject.getJSONArray("results");
        for (int i=0;i<arrayList.size();i++){
            JSONObject object = arrayList.getJSONObject(i);
            JSONObject rowObj = object.getJSONObject("rows");
            jsonArray.add(rowObj);
        }
        resultObj.put("bookmark",jsonObject.getString("bookmark"));
        resultObj.put("hasNextPage",hasNextPage);
        resultObj.put("data",jsonArray);
        return resultObj;
    }
    /**
     * @Description:区块信息方法返回数据解剖
     * @Author: tian.changlong
     * @Date: 2022/10/21 11:02
     * @param data:
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public static JSONObject blockDataReturnAnatomy(Object data){
        JSONObject resultObj=new JSONObject();
        BlockChainInfo blockChainInfo= (BlockChainInfo) data;
        resultObj.put("time",blockChainInfo.getTime());
        resultObj.put("blockNum",blockChainInfo.getBlockNum());
        resultObj.put("stateHash",blockChainInfo.getStateHash());
        resultObj.put("previousBlockHash",blockChainInfo.getPreviousBlockHash());
        List<TxInfo> txInfoList=blockChainInfo.getTxInfoList();
        resultObj.put("dealNum",txInfoList.size());
        JSONArray jsonArray=new JSONArray();
        for (int i=0;i<txInfoList.size();i++) {
            TxInfo txInfo = txInfoList.get(i);
            JSONObject txJson=new JSONObject();
            txJson.put("channel", txInfo.getChannel());
            txJson.put("txId", txInfo.getTxId());
            txJson.put("txType", txInfo.getTxType());
            txJson.put("validationCode", txInfo.getValidationCode());
            txJson.put("chainCode", txInfo.getChainCode());
            jsonArray.add(txJson);
        }
        resultObj.put("txData",jsonArray);
        return resultObj;
    }
}
