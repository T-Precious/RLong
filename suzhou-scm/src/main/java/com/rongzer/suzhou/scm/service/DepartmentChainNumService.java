package com.rongzer.suzhou.scm.service;

import com.rongzer.suzhou.scm.pojo.param.EnterpriseParam;
import com.rongzer.suzhou.scm.pojo.param.PageParam;
import com.rongzer.suzhou.scm.vo.ReturnResult;

/**
 * @Description:
 * @Author: Administrator
 * @Date: 2023/2/7 9:51
 */
public interface DepartmentChainNumService {
    /**
     * 根据日期类型统计部门的的上链数量
     * @param DateType
     * @return
     */
    ReturnResult countByDateType(String DateType);

    /**
     *  将ADM_LICENSE  ADM_PENALTY 两张表中的数据  按照归集部门统计数量  存放至Department_Chain_Num表中
     * @return
     */
    ReturnResult gatherAdm(String syncDate);

    /**
     * 根据条件获取业务对应的数据
     * @param enterpriseParam
     * @param pageParam
     * @return
     */
    ReturnResult getBusinessData(EnterpriseParam enterpriseParam, PageParam pageParam);


    /**
     * 根据条件导出业务数据
     * @param enterpriseParam
     * @param pageParam
     */
    void exportBusinessData(EnterpriseParam enterpriseParam, PageParam pageParam);
}
