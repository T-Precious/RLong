package com.rongzer.suzhou.scm.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * controller返回前台的json封装对象，为了统一命名规范
 *
 * @author tiancl
 */
@Getter
@Setter
public class ReturnPageResult implements Serializable {

    private static final long serialVersionUID = -2390168297189636415L;

    /**
     * 状态码：请保重统一规范 status=1 表示成功，status=0 表示失败
     */
    private int status;

    /**
     * 返回的提示信息
     */
    private String msg;

    /**
     * 返回的主体数据
     */
    private Object data;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 总条数
     */
    private Long totalRows;

    /**
     * 当前初始多少页
     */
    private int currentPage = 1;
}
