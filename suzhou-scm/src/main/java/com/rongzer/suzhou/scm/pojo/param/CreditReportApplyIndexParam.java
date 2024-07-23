package com.rongzer.suzhou.scm.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author tiancl
 * @Date 2022/10/20 14:02
 * @Version 1.0
 **/
@Getter
@Setter
public class CreditReportApplyIndexParam {
    /**
     * 身份证号码
     **/
      private String certNum;
    /**
     * 报告编号
     **/
      private String reportNum;
    /**
     * 统一社会信用代码
     **/
      private String uscc;
    /**
     * 创建时间
     **/
      private String createTime;
    /**
     * 每页数据条数
     **/
      @NotNull(message = "pageSize不能为空")
      private Integer pageSize;
    /**
     * 分页查询下一页的标识
     **/
      private String bookMark;

    @Override
    public String toString() {
        return "CreditReportApplyIndexParam{" +
                "certNum='" + certNum + '\'' +
                ", reportNum='" + reportNum + '\'' +
                ", uscc='" + uscc + '\'' +
                ", createTime='" + createTime + '\'' +
                ", pageSize=" + pageSize +
                ", bookMark='" + bookMark + '\'' +
                '}';
    }
}
