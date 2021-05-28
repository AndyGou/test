package com.system.enumeration;

/**
 * @author Luffy
 * @description 用户角色枚举
 * @createTime 2021年05月08日 11:13:00
 */
public enum RoleEnum {

    ORDINARY(0,"普通用户","scm数据查看权限"),
    ADMINISTRATOR(1,"管理员","scm数据增删改查权限"),
    SUPER_ADMINISTRATOR(2,"超级管理员","scm和account数据增删改查权限");

    private Integer roleCode;

    private String roleDescription;

    private String permissions;

    RoleEnum(Integer roleCode, String roleDescription,String permissions) {
        this.roleCode = roleCode;
        this.roleDescription = roleDescription;
        this.permissions = permissions;
    }

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
