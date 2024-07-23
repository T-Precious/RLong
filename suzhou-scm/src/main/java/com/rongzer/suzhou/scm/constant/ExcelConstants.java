package com.rongzer.suzhou.scm.constant;

import com.rongzer.suzhou.scm.pojo.Column;

import java.util.ArrayList;
import java.util.List;


/**
 * 维护每个业务excel导出的列
 * @author pshe
 */
public class ExcelConstants {

    public static final List<Column> XYZHPJ = new ArrayList<>();
    public static final List<Column> XYBG = new ArrayList<>();
    public static final List<Column> XYSC = new ArrayList<>();
    public static final List<Column> XYCN = new ArrayList<>();
    public static final List<Column> XYXF = new ArrayList<>();
    public static final List<Column> YYSS = new ArrayList<>();
    public static final List<Column> XZXK = new ArrayList<>();
    public static final List<Column> XZCF = new ArrayList<>();


    static {
        XYZHPJ.add(new Column("NAME","企业名称"));
        XYZHPJ.add(new Column("USCC","统一社会信用代码"));
        XYZHPJ.add(new Column("ASSESS_SCORE","评分"));
        XYZHPJ.add(new Column("RATING_LEVEL_NAME","评级"));
        XYZHPJ.add(new Column("ASSESS_UPDATE_DATE","评级时间"));

        XYBG.add(new Column("APPLY_NUM","申请编号"));
        XYBG.add(new Column("NAME","企业名称"));
        XYBG.add(new Column("USCC","统一社会信用代码"));
        XYBG.add(new Column("MANAGER_USERNAME","经办人姓名"));
        XYBG.add(new Column("APPLY_TIME","申请时间"));
//        XYBG.add(new Column("PARK_STATUS","园区状态"));
//        XYBG.add(new Column("PROVINCE_CITY_STATUS","省级状态"));
        XYBG.add(new Column("REPORT_LEVEL","报告级"));

        XYSC.add(new Column("APPLY_NUM","申请编号"));
        XYSC.add(new Column("APPLY_USERNAME","申请人"));
        XYSC.add(new Column("APPLY_MATTER","申请事由"));
        XYSC.add(new Column("APPLY_UNIT","申请单位"));
//        XYSC.add(new Column("PARK_REVIEW_STATUS","园区及审查状态"));
        XYSC.add(new Column("REVIEW_LEVEL","审查级别"));
        XYSC.add(new Column("ADM_COUNTERPART_CAT","行政相对类别"));
        XYSC.add(new Column("APPLY_TIME","申请时间"));
        XYSC.add(new Column("REVIEW_NUM","审查数量"));
        XYSC.add(new Column("DISCREDIT_NUM","失信数量"));
        XYSC.add(new Column("HEAVIER_SERIOUS_DISCREDIT_NUM","严重失信数量"));

//        XYCN.add(new Column("DEAL_DEPT_NAME","部门名称"));
//        XYCN.add(new Column("MATTER_NAME","事项名称"));
//        XYCN.add(new Column("APPLY_NUM","申请次数"));
//        XYCN.add(new Column("FINISH_NUM","已办结数量"));
//        XYCN.add(new Column("WAIT_NUM","待审核数量"));
//        XYCN.add(new Column("REJECT_NUM","已驳回数量"));
        XYCN.add(new Column("PROMISOR_NAME","承诺人名称"));
        XYCN.add(new Column("PROMISOR_TYPE","承诺人类别"));
        XYCN.add(new Column("PROMISOR_NUM","承诺人代码"));
        XYCN.add(new Column("COMMITMENT_TYPE","承诺类型"));
        XYCN.add(new Column("COMMIT_DATE","承诺作出日期"));
        XYCN.add(new Column("ACCEPTANCE_UNIT","承诺受理单位"));
        XYCN.add(new Column("COLLECTION_DEPARTMENT","归集部门"));
        XYCN.add(new Column("PERFORMANCE_STATUS","承诺履行状态"));

        XYXF.add(new Column("APPLY_NUM","信用修复编号"));
        XYXF.add(new Column("NAME","企业名称"));
        XYXF.add(new Column("USCC","统一社会信用代码"));
        XYXF.add(new Column("COLLECTION_DEPARTMENT","业务主管部门"));
        XYXF.add(new Column("APPLY_TIME","申请日期"));
        XYXF.add(new Column("CURRENT_STATUS","状态"));
        XYXF.add(new Column("CREATE_TIME","审核截止日期"));


        YYSS.add(new Column("APPLY_NUM","编号"));
        YYSS.add(new Column("NAME","主体名称"));
        YYSS.add(new Column("USCC","统一社会信用代码"));
        YYSS.add(new Column("APPLY_DEPT","主管部门"));
        YYSS.add(new Column("CONTACT_PERSON","政府部门经办人"));
        YYSS.add(new Column("APPLY_TIME","申请日期"));
        YYSS.add(new Column("CREDIT_AUDIT_TIME","办结时间"));
        YYSS.add(new Column("STATUS","状态"));


        XZXK.add(new Column("ADM_COUNTERPART","行政相对人名称"));
        XZXK.add(new Column("ADM_COUNTERPART_CAT","行政相对人类别"));
        XZXK.add(new Column("CERT_NUM","证件号码/统一社会信用代码"));
        XZXK.add(new Column("ADM_LIC_DECIDE_NUM","许可决定文书号"));
        XZXK.add(new Column("LICENSE_DEC_DATE","许可决定日期"));
        XZXK.add(new Column("SOURCE_CREATE_DATE","许可入库时间"));
        XZXK.add(new Column("LICENSING_AUTH","许可机关"));
        XZXK.add(new Column("COLLECTION_DEPARTMENT","归集部门"));
        XZXK.add(new Column("DOCKING_METHOD","对接方式"));

        XZCF.add(new Column("ADM_COUNTERPART","行政相对人名称"));
        XZCF.add(new Column("ADM_COUNTERPART_CAT","行政相对人类别"));
        XZCF.add(new Column("CERT_NUM","证件号码/统一社会信用代码"));
        XZCF.add(new Column("ADM_PENALTY_DECIDE_NUM","处罚决定文书号"));
        XZCF.add(new Column("PUNISHMENT_DEC_DATE","处罚决定日期"));
        XZCF.add(new Column("SOURCE_CREATE_DATE","处罚入库时间"));
        XZCF.add(new Column("PUNISHMENT_AUTH","处罚机关"));
        XZCF.add(new Column("COLLECTION_DEPARTMENT","归集部门"));
        XZCF.add(new Column("DOCKING_METHOD","对接方式"));

    }

}
