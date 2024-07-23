package com.rongzer.suzhou.scm.controller;

import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.suzhou.scm.pojo.param.SyncRBCTableDataJobParam;
import com.rongzer.suzhou.scm.service.DepartmentChainNumService;
import com.rongzer.suzhou.scm.service.LeadCockpitService;
import com.rongzer.suzhou.scm.service.MysqlSyncBlockchainService;
import com.rongzer.suzhou.scm.service.RBCSyncMysqlService;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2023/2/14 10:09
 * @Version 1.0
 **/
@Slf4j
@RestController
public class VerificationScheduledTaskController {
    @Autowired
    private LeadCockpitService leadCockpitService;
    @Autowired
    private RBCSyncMysqlService rbcSyncMysqlService;
    @Autowired
    private MysqlSyncBlockchainService mysqlSyncBlockchainService;
    @Autowired
    private DepartmentChainNumService departmentChainNumService;
    /**
     * @Description:数据同步(区块链到mysql)
     * @Author: tian.changlong
     * @Date: 2023/2/14 10:10
     * @return: void
     **/
    @PostMapping("/suzhouModelTableDataSyncByIndex")
    public void suzhouModelTableDataSyncByIndex(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        rbcSyncMysqlService.suzhouModelTableDataSyncByIndexAndTables("suzhouCreditBank", "rows.SYNC_DATE", syncRBCTableDataJobParam.getSyncDate(),syncRBCTableDataJobParam.getTables());
    }
    /**
     * @Description:数据同步(mysql到区块链)
     * @Author: tian.changlong
     * @Date: 2023/2/14 10:10
     * @return: void
     **/
    @PostMapping("/suzhouMysqlTableDataSyncAndRbc")
    public void suzhouMysqlTableDataSyncAndRbc(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        mysqlSyncBlockchainService.suzhouMysqlTableDataSyncAndRbc("suzhouCreditBank", "SYNC_DATE", syncRBCTableDataJobParam.getSyncDate(),syncRBCTableDataJobParam.getTables());
    }
    @PostMapping("/selectSevenBusinessTypeNum")
    public void selectSevenBusinessTypeNum() {
        ReturnResult returnResult=leadCockpitService.selectBusinessTypeNum();
        if(returnResult.getStatus()==1){
            log.info("method:selectSevenBusinessTypeNum,查询7大业务类型记录总数成功");
        }else{
            log.error("method:selectSevenBusinessTypeNum,查询7大业务类型记录总数异常："+returnResult.getMsg());
        }
    }
    @PostMapping("/selectAllModelNum")
    public void selectAllModelNum() {
        ReturnResult returnResult=leadCockpitService.selectAllNum();
        if(returnResult.getStatus()==1){
            log.info("method:selectAllModelNum,查询8个模型记录总数成功");
        }else{
            log.error("method:selectAllModelNum,查询8个模型记录总数异常："+returnResult.getMsg());
        }
    }
    @PostMapping("/gatherAdmLicense")
    public void gatherAdmLicense(@RequestParam(name = "syncDate")String syncDate) {
        ReturnResult returnResult=departmentChainNumService.gatherAdm(syncDate);
        if(returnResult.getStatus()==1){
            log.info("method:gatherAdmLicense,归集数据成功");
        }else{
            log.error("method:gatherAdmLicense,归集数据异常："+returnResult.getMsg());
        }
    }
    /**
     * @Description:查询区块链中某个表格某个时间新增的数据
     * @Author: tian.changlong
     * @Date: 2023/7/6 10:57
     * @param syncRBCTableDataJobParam:
     * @return: void
     **/
    @PostMapping("/selectTableDataBySyncDate")
    public String selectTableDataBySyncDate(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=leadCockpitService.selectTableDataBySyncDate(syncRBCTableDataJobParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:询区块链中某个表格某个时间新增的数据数量
     * @Author: tian.changlong
     * @Date: 2023/12/7 15:40
     * @param syncRBCTableDataJobParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectTableDataNumBySyncDate")
    public String selectTableDataNumBySyncDate(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=leadCockpitService.selectTableDataNumBySyncDate(syncRBCTableDataJobParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:根据具体索引查询数据
     * @Author: tian.changlong
     * @Date: 2023/8/29 17:45
     * @param syncRBCTableDataJobParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectTableDataByIndex")
    public String selectTableDataByIndex(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=leadCockpitService.selectTableDataByIndex(syncRBCTableDataJobParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:根据具体字段排序查询数据
     * @Author: tian.changlong
     * @Date: 2023/12/21 15:33
     * @param syncRBCTableDataJobParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectTableDataBySort")
    public String selectTableDataBySort(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=leadCockpitService.selectTableDataBySort(syncRBCTableDataJobParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:上链数据的删除
     * @Author: tian.changlong
     * @Date: 2023/8/14 18:08
     * @param syncRBCTableDataJobParam:
     * @return: java.lang.String
     **/
    @PostMapping("/deleteTableDataBySyncDate")
    public String deleteTableDataBySyncDate(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=leadCockpitService.deleteTableDataBySyncDate(syncRBCTableDataJobParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:上链数据根据rowID删除
     * @Author: tian.changlong
     * @Date: 2023/8/17 18:52
     * @param syncRBCTableDataJobParam:
     * @return: java.lang.String
     **/
    @PostMapping("/deleteTableDataByRowId")
    public String deleteTableDataByRowId(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=leadCockpitService.deleteTableDataByRowId(syncRBCTableDataJobParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:mysql中数据的删除
     * @Author: tian.changlong
     * @Date: 2023/8/14 18:50
     * @param syncRBCTableDataJobParam:
     * @return: java.lang.String
     **/
    @PostMapping("/deleteMysqlTableDataByIndex")
    public String deleteMysqlTableDataByIndex(@Valid @RequestBody SyncRBCTableDataJobParam syncRBCTableDataJobParam) {
        ReturnResult returnResult=mysqlSyncBlockchainService.deleteMysqlTableDataByIndex(syncRBCTableDataJobParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
}
