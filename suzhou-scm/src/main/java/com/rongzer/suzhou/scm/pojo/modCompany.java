package com.rongzer.suzhou.scm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/15 13:59
 * @Version 1.0
 **/
@Getter
@Setter
@TableName(value = "MOD_COMPANY")
public class modCompany {
    /**
     * 企业名称
     */
    @TableId(value = "NAME")
    private String name;
    /**
     * 企业名称
     */
    @TableId(value = "USCC")
    private String uscc;
    /**
     * 评分
     */
    @TableId(value = "ASSESS_SCORE")
    private String assessScore;
    /**
     * 评级
     */
    @TableId(value = "RATING_LEVEL_NAME")
    private String ratingLevelName;
    /**
     * 评级时间
     */
    @TableId(value = "ASSESS_UPDATE_DATE")
    private String assessUpdateDate;
}
