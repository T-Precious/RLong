package com.rongzer.suzhou.scm.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Created by luolingling on 2019/2/20
 * 同步区块链模型表数据到数据库
 */
public class UpdateDataThreadPoolTask implements Callable<Integer>, Serializable {


    private static final long serialVersionUID = 0;
    //日志
    Logger log = LoggerFactory.getLogger("syncDataLogger");
    // 保存任务所需要的数据
    private String queryTableName;
    private String chaincodeName;
    private String modelName;
    private String tableName;
    private String index;

    public UpdateDataThreadPoolTask(String queryTableName, String chaincodeName, String modelName, String tableName, String index) {
        this.queryTableName = queryTableName;
        this.chaincodeName = chaincodeName;
        this.modelName = modelName;
        this.tableName = tableName;
        this.index = index;
    }

    @Override
    public synchronized Integer call() throws Exception {
        try {
//            RBCSyncMysqlService rbcSyncMysqlService = SpringContextUtil.getBean(RBCSyncMysqlService.class);
//            rbcSyncMysqlService.queryAndSaveModelData(queryTableName, chaincodeName, modelName, tableName, index);
        } catch (Exception e) {
            log.error("执行异常", e);
        }
        return null;

    }


}
