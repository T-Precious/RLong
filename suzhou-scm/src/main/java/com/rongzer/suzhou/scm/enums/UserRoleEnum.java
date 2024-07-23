package com.rongzer.suzhou.scm.enums;

/**
 * 用户角色枚举
 */
public enum UserRoleEnum {

    FinanceDEPT("FinanceDEPT", "财务部"),
    PurchaseDEPT("PurchaseDEPT", "采购部"),
    Purchaser("Purchaser", "经销商"),
    Supplier("Supplier", "供应商"),
    SalesDEPT("SalesDEPT", "销售部"),
    PigFarm("PigFarm", "猪场"),
    UNKNOWN("Unknown", "未知");

    private String roleNo;

    private String roleName;

    UserRoleEnum(String roleNo, String roleName) {
        this.roleNo = roleNo;
        this.roleName = roleName;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public String getRoleName() {
        return roleName;
    }

    public static UserRoleEnum get(String roleName) {
        for (UserRoleEnum roleEnum : UserRoleEnum.values()) {
            if (roleEnum.roleNo.equals(roleName)) {
                return roleEnum;
            }
        }

        return UNKNOWN;
    }
}
