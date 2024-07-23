package com.rongzer.suzhou.scm.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.constant.Consts;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2021/12/27
 * Description:
 */
@Getter
@Setter
public class BaseTableData {

    /**
     * 会员编号
     */
    @JsonProperty(value = "CUSTOMER_NO")
    @TableField(value = "CUSTOMER_NO")
    private String customerNo = Consts.CUSTOMER_NO;

    /**
     * 录入账号（手机号）
     */
    private String inputUser;

    /**
     * 录入日期
     */
    private String inputDate;

    /**
     * 录入时间
     */
    private String inputTime;

    public BaseTableData() {
        LocalDateTime now = LocalDateTime.now();
        this.inputDate = now.format(DateTimeFormatter.ISO_DATE);
        this.inputTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}