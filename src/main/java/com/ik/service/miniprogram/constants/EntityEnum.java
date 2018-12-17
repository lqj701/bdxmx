package com.ik.service.miniprogram.constants;

public enum EntityEnum {

    organization("Organization", "uc_organizations"),

    user("User", "uc_users"),

    department("Department", "uc_departments"),

    userDepartment("UserDepartment", "uc_users_departments"),

    userAssistDepartment("UserAssistDepartment", "uc_users_assist_departments"),

    roleUser("RoleUser", "uc_roles_users"),

    account("Account", "uc_accounts"),

    organizationDockingBms("OrganizationDockingBms", "uc_organization_docking_bms");

    EntityEnum(String classSimpleName, String tableName) {
        this.classSimpleName = classSimpleName;
        this.tableName = tableName;
    }

    private String classSimpleName;
    private String tableName;

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public String getTableName() {
        return tableName;
    }

    public static EntityEnum getEntityEnum(String entity) {
        for (EntityEnum entityEnum : EntityEnum.values()) {
            if (entityEnum.name().equals(entity)) {
                return entityEnum;
            }
        }
        return null;
    }
}
