package com.rongzer.suzhou.scm.constant;


import com.rongzer.suzhou.scm.client.SuzhouAgrClient;

/**
 * Created with IntelliJ IDEA.
 * User: tiancl
 * Date: 2022/10/13
 * Description:
 */
public class Consts {

    /**
     * admin用户会员编号
     */
    public static final String CUSTOMER_NO = "admin";

    public static SuzhouAgrClient client = new SuzhouAgrClient();
    /**
     * 信用报告申请表
     */
    public static final String T_CREDIT_REPORT_APPLY="T_CREDIT_REPORT_APPLY";
    /**
     * 法人综合信用评价信息
     */
    public static final String MOD_COMPANY="MOD_COMPANY";
    /**
     * 行政许可（变更）信息  ---双公示
     */
    public static final String ADM_LICENSE="ADM_LICENSE";
    /**
     * 行政处罚信息  ---双公示
     */
    public static final String ADM_PENALTY="ADM_PENALTY";
    /**
     * 信用修复申请表
     */
    public static final String T_CREDIT_REPAIR_APPLY="T_CREDIT_REPAIR_APPLY";
    /**
     * 异议申诉申请表
     */
    public static final String T_CREDIT_OBJECTION_APPEAL_APPLY="T_CREDIT_OBJECTION_APPEAL_APPLY";
    /**
     * 信用承诺信息
     */
    public static final String CREDIT_COMMITMENT_2022="CREDIT_COMMITMENT_2022";

    public static final Integer WIDERANGE_ROW_NUM=1000;

    public static final Integer ROW_NUM=100;

    public static final int  LEGAL_UPCHAINS_TYPE=1;

    public static final int  PERSONAL_UPCHAINS_TYPE=2;

    public static final int PAGE_SIZE=500;

    public static final String CONSUMERID="cc77e160-486e-11ee-be56-0242ac120002";

    public static final String CONSUMERID2="69f2f6d8-4ae9-11ee-be56-0242ac120002";

    public static final StringBuilder COCHAIN_SWITCH=new StringBuilder("OFF");

    public static final String TODAY_FINALTIME="23:59:59";

    public static final String TODAY_STARTTIME="00:00:00";
}