package com.rongzer.suzhou.scm.enums;

/**
 * pdf类型枚举
 */
public enum PdfEnum {

    xsht("xsht", "templates/xsht.docx", "销售合同", "./saleContractDetail"),
    cght("cght", "templates/cght.docx", "采购合同", "./purchaseSaleContractDetail"),
    yfpz("yfpz", "templates/yfpz.docx", "应付凭证", "./payInfoDetail"),
    yspz("yspz", "templates/yspz.docx", "应收凭证", "./receiveInfoDetail");

    private String value;

    private String templatePath;

    private String description;

    private String navigateUrl;

    PdfEnum(String value, String templatePath, String description, String navigateUrl) {
        this.value = value;
        this.templatePath = templatePath;
        this.description = description;
        this.navigateUrl = navigateUrl;
    }

    public String getValue() {
        return value;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public String getDescription() {
        return description;
    }

    public String getNavigateUrl() {
        return navigateUrl;
    }

}
