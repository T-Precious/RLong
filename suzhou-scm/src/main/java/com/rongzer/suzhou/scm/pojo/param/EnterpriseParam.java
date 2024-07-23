package com.rongzer.suzhou.scm.pojo.param;

import com.rongzer.suzhou.scm.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * @Description: 信用区块链平台链上数据查询与溯源界面的查询参数
 * @Author: Administrator
 * @Date: 2023/2/8 10:33
 */
@Getter
@Setter
public class EnterpriseParam {
    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 时间
     */
    private String dateType;

    private String startDate;

    private String endDate;

    /**
     * 业务
     */
    private String[] businesses;


    /**
     * 部门
     */
    private String[] departments;


    /**
     * 当前页码
     */
    private Integer currentPage;
    /**
     * 每页条数
     */
    private Integer pageSize;


    /**
     * 将一周  一天  一月 一年  转换为正常时间范围
     * @param dateType
     */
    public void setDateType(String dateType) {
        this.dateType = dateType;
        if(!StringUtils.isEmpty(dateType)){
            switch(dateType){
                case "day":
                    String currentDate = DateUtil.getCurrentDate();
                    this.startDate = currentDate;
                    this.endDate = currentDate;
                    break;
                case "week":
                    String[] dateScopeWeek = DateUtil.getDateScopeWeek();
                    this.startDate = dateScopeWeek[0];
                    this.endDate = dateScopeWeek[1];
                    break;
                case "month":
                    String[] dateScopeMonth = DateUtil.getDateScopeMonth();
                    this.startDate = dateScopeMonth[0];
                    this.endDate = dateScopeMonth[1];
                    break;
                case "year":
                    String[] dateScopeYear = DateUtil.getDateScopeYear();
                    this.startDate = dateScopeYear[0];
                    this.endDate = dateScopeYear[1];
                    break;
            }
        }
    }
}
