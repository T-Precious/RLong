package com.rongzer.suzhou.scm.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 在进行sdk调用之前,配置公共参数用于和区块链交互
 * @Author tiancl
 * @Date 2022/10/13 10:21
 * @Version 1.0
 **/
@Getter
@Setter
@Component
public class CommonProperties {
    /**
     * 链接配置文件,文件里peer和orderer的tls证书也是从部署好的区块链环境中获取
     */
    @Value("${connectionTls}")
    private String connectCfg;

    /**
     * 用户证书
     **/
    @Value("${rbc24Cert}")
    private String walletDir;

    /**
     * 组织管理员私钥（查询节点安装合约数量时需要，其他不需要）
     **/
    @Value("${keyFilePath}")
    private String keyFilePath;

    /**
     * 组织管理员证书（查询节点安装合约数量时需要，其他不需要）
     **/
    @Value("${certFilePath}")
    private String certFilePath;

    /**
     * 模型名称
     **/
    @Value("${modelName}")
    private String modelName;

    /**
     * 合约名称
     **/
    @Value("${chainCodeName}")
    private String chainCodeName;

    /**
     * 合约版本号
     **/
    @Value("${chainCodeVersion}")
    private String chainCodeVersion;

    /**
     * 通道名称
     **/
    @Value("${channelName}")
    private String channelName;

    @Value("${tableNames}")
    private String tableNames;

    /**
     * 按照主体类型区分既包含法人数据又包含个人数据的表结构
     **/
    @Value("${subjectType.legal.personal.tables}")
    private String subTypeLegalPersonal;
    /**
     *按照行政相对人类别区分既包含法人数据又包含个人数据的表结构
     **/
    @Value("${admCounterpartCat.legal.personal.tables}")
    private String admCatLegalPersonal;
    /**
     *只包含法人数据的表结构
     **/
    @Value("${legal.tables}")
    private String legalTables;
    /**
     *上链开始时间
     **/
    @Value("${cochain.start.time}")
    private String cochainStartTime;
    /**
     *上链结束时间
     **/
    @Value("${cochain.end.time}")
    private String cochainEndTime;
}
