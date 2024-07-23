package com.rongzer.suzhou.scm.service;

import com.rongzer.sdk.rbaas.response.ApiResponse;
import com.rongzer.suzhou.scm.pojo.param.*;
import com.rongzer.suzhou.scm.vo.ReturnResult;

public interface LeadCockpitService {
    ApiResponse selectCreditDataLinkTrend();
    ReturnResult getRowCount();
    ReturnResult getTableRowCount(SyncRBCTableDataJobParam syncRBCTableDataJobParam);
    ReturnResult selectCreditApplication(CreditApplicationParam creditApplicationParam);
    ReturnResult selectLegalNumByIndex();
    ReturnResult selectPersonalNumByIndex();
    ReturnResult selectUpchainsNumber(RealTimeBlockChainParam realTimeBlockChainParam);
    ReturnResult selectSevenBusinessUpchainsNumber();
    ReturnResult selectCreditDataUpchainsNumber(CreditDataUpchainsParam creditDataUpchainsParam);
    ReturnResult selectOnChainGrowthTrend(CreditDataUpchainsParam creditDataUpchainsParam);
    ReturnResult selectRealTimeDataUpchainsNumber(RealTimeDataUpchainsParam realTimeDataUpchainsParam);
    ReturnResult selectEnterpriseInfoByUscc(ForEnterprisesParam forEnterprisesParam);
    ReturnResult selectEnterpriseMysqlByUscc(ForEnterprisesParam forEnterprisesParam);
    ReturnResult selectBusinessTypeNum();
    ReturnResult selectTableDataBySyncDate(SyncRBCTableDataJobParam syncRBCTableDataJobParam);
    ReturnResult selectTableDataNumBySyncDate(SyncRBCTableDataJobParam syncRBCTableDataJobParam);
    ReturnResult selectTableDataByIndex(SyncRBCTableDataJobParam syncRBCTableDataJobParam);
    ReturnResult selectTableDataBySort(SyncRBCTableDataJobParam syncRBCTableDataJobParam);
    ReturnResult deleteTableDataBySyncDate(SyncRBCTableDataJobParam syncRBCTableDataJobParam);
    ReturnResult deleteTableDataByRowId(SyncRBCTableDataJobParam syncRBCTableDataJobParam);


    /**
     * 查询ADM_LICENSE,ADM_PENALTY,MOD_COMPANY,
     * T_CREDIT_REPAIR_APPLY,T_CREDIT_REPORT_APPLY,
     * T_RED_CREDIT_EVAL_TAX,CREDIT_COMMITMENT_2022,T_CREDIT_OBJECTION_APPEAL_APPLY
     * 这8个模型的所有数量
     * @return
     */
    ReturnResult selectAllNum();
}
