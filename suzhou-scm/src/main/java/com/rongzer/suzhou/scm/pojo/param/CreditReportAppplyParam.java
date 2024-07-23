package com.rongzer.suzhou.scm.pojo.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongzer.suzhou.scm.validation.IsDate;
import com.rongzer.suzhou.scm.validation.IsDateTime;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * @Description TODO
 * @Author tiancl
 * @Date 2022/10/17 17:29
 * @Version 1.0
 **/
@Getter
@Setter
public class CreditReportAppplyParam {
    /**
     * 主键
     **/
    @JsonProperty("ID")
    private String id;
    /**
     * 申请部门
     **/
    @JsonProperty("APPLY_DEPT")
    private String applyDept;
    /**
     * 申请文件
     **/
    @JsonProperty("APPLY_FILE_ID")
    private String applyFileId;
    /**
     * 申请编号
     **/
    @JsonProperty("APPLY_NUM")
    private String applyNum;
    /**
     * 申请项目名称
     **/
    @JsonProperty("APPLY_PROJECT_NAME")
    private String applyProjectName;
    /**
     * 申请时间
     **/
    @IsDateTime(message = "applyTime格式不正确")
    @JsonProperty("APPLY_TIME")
    private String applyTime;
    /**
     * 审核时间
     **/
    @IsDateTime(message = "auditTime格式不正确")
    @JsonProperty("AUDIT_TIME")
    private String auditTime;
    /**
     * 审核人
     **/
    @JsonProperty("AUDITOR")
    private String auditor;
    /**
     * 营业执照
     **/
    @JsonProperty("BUSINESS_LICENSE_ID")
    private String businessLicenseId;
    /**
     * 营业执照缩略图
     **/
    @JsonProperty("BUSINESS_LICENSE_THUMBNAIL_ID")
    private String businessLicenseThumebnailId;
    /**
     * 身份证号码
     **/
    @JsonProperty("CERT_NUM")
    @NotBlank(message = "certNum不能为空")
    private String certNum;
    /**
     * 认证书编码
     **/
    @JsonProperty("CERTIFICATE_NUM")
    private String certificateNum;
    /**
     * 市
     **/
    @JsonProperty("CITY")
    private String city;
    /**
     * 区
     **/
    @JsonProperty("COUNTY")
    private String county;
    /**
     * 创建时间
     **/
    @IsDateTime(message = "createTime格式不正确")
    @JsonProperty("CREATE_TIME")
    private String createTime;
    /**
     * 创建人
     **/
    @JsonProperty("CREATOR")
    private String creator;
    /**
     * 删除状态
     **/
    @JsonProperty("DELETED")
    private Integer deleted;
    /**
     * 邮寄单号
     **/
    @JsonProperty("MAILING_NUMBER")
    private String mailingNumber;
    /**
     * 经办人身份证号
     **/
    @JsonProperty("MANAGER_CERT_NUM")
    private String managerCertNum;
    /**
     * 经办人邮箱
     **/
    @JsonProperty("MANAGER_EMAIL")
    private String managerEmail;
    /**
     * 经办人联系电话
     **/
    @JsonProperty("MANAGER_TEL")
    private String managerTel;
    /**
     * 经办人姓名
     **/
    @JsonProperty("MANAGER_USERNAME")
    @NotBlank(message = "managerUserName不能为空")
    private String managerUserName;
    /**
     * 修改人
     **/
    @JsonProperty("MODIFIER")
    private String modifier;
    /**
     * 修改时间
     **/
    @IsDateTime(message = "modifyTime格式不正确")
    @JsonProperty("MODIFY_TIME")
    private String modifyTime;
    /**
     * 机构全称/自然人名称
     **/
    @JsonProperty("NAME")
    private String name;
    /**
     * 组织机构代码
     **/
    @JsonProperty("ORG_CODE")
    @NotBlank(message = "orgCode不能为空")
    private String orgCode;
    /**
     * 园区信用报告
     **/
    @JsonProperty("PARK_CREDIT_ID")
    private String parkCreditId;
    /**
     * 手机
     **/
    @JsonProperty("PHONE")
    private String phone;
    /**
     * 身份证正面照
     **/
    @JsonProperty("POSITIVE_FILE_ID")
    private String positiveFileId;
    /**
     * 身份证正面照缩略图
     **/
    @JsonProperty("POSITIVE_FILE_THUMBNAIL_ID")
    private String positiveFileThumbnailId;
    /**
     * 项目细类
     **/
    @JsonProperty("PROJECT_FINE")
    private String projectFine;
    /**
     * 省
     **/
    @JsonProperty("PROVINCE")
    private String province;
    /**
     * 省市信用报告
     **/
    @JsonProperty("PROVINCES_CITY_ID")
    private String provincesCityId;
    /**
     * 资质认证编码
     **/
    @JsonProperty("QUALIFICATION_NUM")
    private String qualificationNum;
    /**
     * 驳回原因
     **/
    @JsonProperty("REASON_REJECTION")
    private String reasonRejection;
    /**
     * 领取方式
     **/
    @JsonProperty("RECEIVE_METHOD")
    private Integer receiveMethod;
    /**
     * 工商注册号
     **/
    @JsonProperty("REG_NUM")
    @NotBlank(message = "regNum不能为空")
    private String regNum;
    /**
     * 备注
     **/
    @JsonProperty("REMARK")
    private String remark;
    /**
     * 报告截止日期
     **/
    @JsonProperty("REPORT_END_DATE")
    @IsDate(message = "reportEndDate格式不正确")
    @NotBlank(message = "reportEndDate不能为空")
    private String reportEndDate;
    /**
     * 报告编号
     **/
    @JsonProperty("REPORT_NUM")
    @NotBlank(message = "reportNum不能为空")
    private String reportNum;
    /**
     * 报告用途
     **/
    @JsonProperty("REPORT_PURPOSE")
    private String reportPurpose;
    /**
     * 报告开始日期
     **/
    @JsonProperty("REPORT_START_DATE")
    @IsDate(message = "reportStartDate格式不正确")
    @NotBlank(message = "reportStartDate不能为空")
    private String reportStartDate;
    /**
     * 身份证正反面复印件
     **/
    @JsonProperty("REVERSE_FILE_ID")
    private String reverseFileId;
    /**
     * 身份证正面复印件缩略图
     **/
    @JsonProperty("REVERSE_FILE_THUMBNAIL_ID")
    private String reverseFileThumbnailId;
    /**
     * 申请来源
     **/
    @JsonProperty("SOURCE")
    private Integer source;
    /**
     * 盖章文件
     **/
    @JsonProperty("STAMP_FILE")
    private String stampFile;
    /**
     * 审核状态
     **/
    @JsonProperty("STATUS")
    private Integer status;
    /**
     * 主体类型
     **/
    @JsonProperty("SUBJECT_TYPE")
    private String subjectType;
    /**
     * 统一社会信用代码
     **/
    @JsonProperty("USCC")
    @NotBlank(message = "uscc不能为空")
    private String uscc;
    /**
     * 调用来源平台
     **/
    @JsonProperty("USE_PLATFORM")
    private String usePlatform;
    /**
     * 调用来源平台UUID
     **/
    @JsonProperty("USE_PLATFORM_CODE")
    private String usePlatformCode;

    @Override
    public String toString() {
        return "CreditReportAppplyParam{" +
                "id='" + id + '\'' +
                ", applyDept='" + applyDept + '\'' +
                ", applyFileId='" + applyFileId + '\'' +
                ", applyNum='" + applyNum + '\'' +
                ", applyProjectName='" + applyProjectName + '\'' +
                ", applyTime='" + applyTime + '\'' +
                ", auditTime='" + auditTime + '\'' +
                ", auditor='" + auditor + '\'' +
                ", businessLicenseId='" + businessLicenseId + '\'' +
                ", businessLicenseThumebnailId='" + businessLicenseThumebnailId + '\'' +
                ", certNum='" + certNum + '\'' +
                ", certificateNum='" + certificateNum + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", createTime='" + createTime + '\'' +
                ", creator='" + creator + '\'' +
                ", deleted=" + deleted +
                ", mailingNumber='" + mailingNumber + '\'' +
                ", managerCertNum='" + managerCertNum + '\'' +
                ", managerEmail='" + managerEmail + '\'' +
                ", managerTel='" + managerTel + '\'' +
                ", managerUserName='" + managerUserName + '\'' +
                ", modifier='" + modifier + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", name='" + name + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", parkCreditId='" + parkCreditId + '\'' +
                ", phone='" + phone + '\'' +
                ", positiveFileId='" + positiveFileId + '\'' +
                ", positiveFileThumbnailId='" + positiveFileThumbnailId + '\'' +
                ", projectFine='" + projectFine + '\'' +
                ", province='" + province + '\'' +
                ", provincesCityId='" + provincesCityId + '\'' +
                ", qualificationNum='" + qualificationNum + '\'' +
                ", reasonRejection='" + reasonRejection + '\'' +
                ", receiveMethod=" + receiveMethod +
                ", regNum='" + regNum + '\'' +
                ", remark='" + remark + '\'' +
                ", reportEndDate='" + reportEndDate + '\'' +
                ", reportNum='" + reportNum + '\'' +
                ", reportPurpose='" + reportPurpose + '\'' +
                ", reportStartDate='" + reportStartDate + '\'' +
                ", reverseFileId='" + reverseFileId + '\'' +
                ", reverseFileThumbnailId='" + reverseFileThumbnailId + '\'' +
                ", source=" + source +
                ", stampFile='" + stampFile + '\'' +
                ", status=" + status +
                ", subjectType='" + subjectType + '\'' +
                ", uscc='" + uscc + '\'' +
                ", usePlatform='" + usePlatform + '\'' +
                ", usePlatformCode='" + usePlatformCode + '\'' +
                '}';
    }
}
