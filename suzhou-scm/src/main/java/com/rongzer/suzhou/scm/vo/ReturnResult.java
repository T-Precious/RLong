package com.rongzer.suzhou.scm.vo;

import java.io.Serializable;

/**
 * controller返回前台的json封装对象，为了统一命名规范
 *
 * @author tiancl
 */
public class ReturnResult implements Serializable {

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
     * 每页记录数
     */
    private int pageSize;

    /**
     *查询下一页的标识
     */
    private String page;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 返回的主体数据
     */
    public Object getData() {
        return data;
    }


    /**
     * 返回的主体数据
     */
    public ReturnResult setData(Object data) {
        this.data = data;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ReturnResult setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }


    /**
     * 默认构造函数，只把resCode设置为1
     */
    public ReturnResult() {
        this.status = 1;
    }

    public ReturnResult(int status) {
        this.status = status;
    }

    public ReturnResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ReturnResult(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }



}
