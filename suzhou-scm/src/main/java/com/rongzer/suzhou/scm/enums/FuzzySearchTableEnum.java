package com.rongzer.suzhou.scm.enums;

/**
 * 支持模糊查询表及字段枚举
 */
public enum FuzzySearchTableEnum {

    SalesContract_contractName("SalesContract", "contractName"),

    PurchaseContract_contractName("PurchaseContract", "contractName"),

    PayableCert_contractName("PayableCert", "contractName"),

    ReceivableCert_contractName("ReceivableCert", "contractName"),

    Logistics_contractName("Logistics", "contractName"),

    Output_contractName("Output", "contractName"),

    Loss_contractName("Loss", "contractName"),

    Purchaser_name("Purchaser", "name"),

    Supplier_name("Supplier",  "name");


    private String tableName;

    private String columnName;

    FuzzySearchTableEnum(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public static FuzzySearchTableEnum get(String tableName, String columnName) {
        for (FuzzySearchTableEnum fsEnum : FuzzySearchTableEnum.values()) {
            if (fsEnum.tableName.equals(tableName) && fsEnum.columnName.equals(columnName)) {
                return fsEnum;
            }
        }
        return null;
    }
}
