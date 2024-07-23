package com.rongzer.suzhou.scm.controller;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.sdk.rbaas.api.RbcModelManager;
import com.rongzer.sdk.rbaas.entity.RowApiEntity;
import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/10/28 9:20
 * @Version 1.0
 **/
@RestController
public class TestController {
    /**
     * 链接配置文件,文件里peer和orderer的tls证书也是从部署好的区块链环境中获取
     */
    @Value("${connectionTls}")
    private String connectCfg1;

    /**
     * 用户证书
     **/
    @Value("${rbc24Cert}")
    private String walletDir1;

    /**
     * 组织管理员私钥（查询节点安装合约数量时需要，其他不需要）
     **/
    @Value("${keyFilePath}")
    private String keyFilePath1;

    /**
     * 组织管理员证书（查询节点安装合约数量时需要，其他不需要）
     **/
    @Value("${certFilePath}")
    private String certFilePath1;

    /**
     * 模型名称
     **/
    @Value("${modelName}")
    private String modelName1;

    /**
     * 合约名称
     **/
    @Value("${chainCodeName}")
    private String chainCodeName1;

    /**
     * 合约版本号
     **/
    @Value("${chainCodeVersion}")
    private String chainCodeVersion1;

    /**
     * 通道名称
     **/
    @Value("${channelName}")
    private String channelName1;


    /**
     * 信息事项下拉列表  预警分析,上报统计
     */
    @GetMapping("dataManage/getInfoList")
    public String getInfoListType() {

        try {
            RowApiEntity request = new RowApiEntity();
            request.setChaincodeName(chainCodeName1);
            request.setChaincodeVersion(chainCodeVersion1);
            request.setJsonConfig(connectCfg1);
            request.setSecurityCert(walletDir1);
            request.setChannelName(channelName1);
            request.setModelName(modelName1);
            request.setTableName("table1");
            request.setRowId("dfgsdgsd3454565472345");

            request.setIsEncrypt(false);
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("ID","dfgsdgsd3454565472345");
            jsonObject2.put("name","张激光");

            request.setTrTdValue(jsonObject2);

            ApiResponse response1 = RbcModelManager.createRow(request, null);
            System.out.println(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
//    return ResponseEntity.getInstance().success(dataReminderService.findInfoList());
        return null;
    }
}
