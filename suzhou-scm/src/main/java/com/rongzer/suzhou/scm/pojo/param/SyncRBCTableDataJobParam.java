package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2023/1/5 18:17
 * @Version 1.0
 **/
@Getter
@Setter
public class SyncRBCTableDataJobParam {
    /**
     * 指定同步表模型
     */
    private String[] tables;
    /**
     * 指定时间 yyyy-MM-dd
     */
    private String syncDate;
    private String rowId;
    private String indexName;
    private String indexValue;
    private String bookmark;
    private String sortField;
}
