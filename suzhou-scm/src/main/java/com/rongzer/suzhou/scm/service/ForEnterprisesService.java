package com.rongzer.suzhou.scm.service;

import com.rongzer.suzhou.scm.pojo.param.ForEnterprisesPageParam;
import com.rongzer.suzhou.scm.pojo.param.ForEnterprisesParam;
import com.rongzer.suzhou.scm.vo.ReturnPageResult;
import com.rongzer.suzhou.scm.vo.ReturnResult;

public interface ForEnterprisesService {
    ReturnPageResult selectAllForEnterprises(ForEnterprisesPageParam forEnterprisesPageParam);
    ReturnResult selectForEnterprisesBySocialCreditCode(ForEnterprisesParam forEnterprisesParam);
    ReturnResult updateForEnterprises(ForEnterprisesParam forEnterprisesParam);
}
