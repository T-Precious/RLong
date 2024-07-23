package com.rongzer.suzhou.scm.service;

import com.rongzer.suzhou.scm.pojo.param.InterfaceUsageParam;
import com.rongzer.suzhou.scm.pojo.param.TappDailyInterfaceParam;
import com.rongzer.suzhou.scm.vo.ReturnResult;

public interface TappDailyInterfaceCallService {
    ReturnResult selectTappDailyInterfaceCallByCondition(InterfaceUsageParam interfaceUsageParam);
    ReturnResult selectAllTappDailyInterfaceCall();
    ReturnResult selectDepartmentTappDailyInterface();
    ReturnResult selectTimePeriodTappDailyInterface(TappDailyInterfaceParam tappDailyInterfaceParam);
    ReturnResult selectApplicationScenarioTappDailyInterface(TappDailyInterfaceParam tappDailyInterfaceParam);
}
