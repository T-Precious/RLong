package com.rongzer.suzhou.scm.service;

import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.pojo.param.*;
import com.rongzer.suzhou.scm.vo.ReturnResult;

public interface SIPCreditService {
    ApiResponse getBlockHeight();
    ReturnResult getNodes();
    ApiResponse getContract();
    ReturnResult getRowHistory(QueryTableParam queryTableParam);
    ApiResponse getBlockInfo();
    ApiResponse getBlockInfoList(QueryBlockParam queryBlockParam);
    ApiResponse addCreditReportApply(CreditReportAppplyParam creditReportAppplyParam);
    ReturnResult updateCreditReportApply(CreditReportAppplyParam creditReportAppplyParam);
    ApiResponse selectDataByPrimaryKey(QueryTableParam queryTableParam);
    ApiResponse selectCreditReportApplyByIndex(CreditReportApplyIndexParam creditReportApplyIndexParam);
    ApiResponse testUploadData(UploadDataParam uploadDataParam);
}
