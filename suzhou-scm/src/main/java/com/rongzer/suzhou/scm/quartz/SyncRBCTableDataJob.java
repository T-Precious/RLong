package com.rongzer.suzhou.scm.quartz;


import com.rongzer.suzhou.scm.service.DepartmentChainNumService;
import com.rongzer.suzhou.scm.service.LeadCockpitService;
import com.rongzer.suzhou.scm.service.RBCSyncMysqlService;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Author: tian.changlong
 * @Date: 2023/1/5 18:32
 * @param null: 
 * @return: null
 **/
@Slf4j
@Component
public class SyncRBCTableDataJob {

    @Autowired
    private RBCSyncMysqlService rbcSyncMysqlService;

    @Autowired
    private LeadCockpitService leadCockpitService;

    @Autowired
    private DepartmentChainNumService departmentChainNumService;

    @Scheduled(cron = "0 59 6 * * ?")
    public void syncLastDayModelData() {
        String yesterday = LocalDate.now().minusDays(1).toString();
        rbcSyncMysqlService.suzhouModelTableDataSyncByIndex("suzhouCreditBank", "rows.SYNC_DATE", yesterday);
    }
    @Scheduled(cron = "0 59 7 * * ?")
    public void syncodayModelData() {
        String today = LocalDate.now().toString();
        rbcSyncMysqlService.suzhouModelTableDataSyncByIndex("suzhouCreditBank", "rows.SYNC_DATE", today);
    }
    @Scheduled(cron = "0 30 8 * * ?")
    public void selectBusinessTypeNum() {
        /*ReturnResult returnResult=leadCockpitService.selectLegalNumByIndex();
        if(returnResult.getStatus()==1){
            log.info("method:selectLegalNumByIndex,查询企业法人表格行记录总数成功");
        }else{
            log.error("method:selectLegalNumByIndex,查询企业法人表格行记录总数异常："+returnResult.getMsg());
        }
        ReturnResult result=leadCockpitService.selectPersonalNumByIndex();
        if(result.getStatus()==1){
            log.info("method:selectLegalNumByIndex,查询自然人表格行记录总数成功");
        }else{
            log.error("method:selectLegalNumByIndex,查询自然人表格行记录总数异常："+result.getMsg());
        }*/

        ReturnResult returnResult=leadCockpitService.selectBusinessTypeNum();
        if(returnResult.getStatus()==1){
            log.info("method:selectBusinessTypeNum,查询7大业务类型记录总数成功");
        }else{
            log.error("method:selectBusinessTypeNum,查询7大业务类型记录总数异常："+returnResult.getMsg());
        }
    }
    //夜里1点到早上9点，每半小时执行一次
    @Scheduled(cron = "0 0/30 1-9 * * ?")
    public void selectAllNum() {
        ReturnResult returnResult=leadCockpitService.selectAllNum();
        if(returnResult.getStatus()==1){
            log.info("method:selectAllNum,查询8个模型记录总数成功");
        }else{
            log.error("method:selectAllNum,查询8个模型记录总数异常："+returnResult.getMsg());
        }
    }
    /**
     * 将ADM_LICENSE  ADM_PENALTY 两张表中的数据  按照归集部门统计数量  存放至Department_Chain_Num表中
     */
    @Scheduled(cron = "0 50 8 * * ?")
    public void gatherAdm() {
        LocalDate localDate = LocalDate.now();
        String yesterday = localDate.plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ReturnResult returnResult=departmentChainNumService.gatherAdm(yesterday);
        if(returnResult.getStatus()==1){
            log.info("method:countAdm,归集数据成功");
        }else{
            log.error("method:countAdm,归集数据异常："+returnResult.getMsg());
        }
    }
    @Scheduled(cron = "0 55 8 * * ?")
    public void gatherTodayAdm() {
        String today = LocalDate.now().toString();
        ReturnResult returnResult=departmentChainNumService.gatherAdm(today);
        if(returnResult.getStatus()==1){
            log.info("method:countAdm,归集数据成功");
        }else{
            log.error("method:countAdm,归集数据异常："+returnResult.getMsg());
        }
    }
}