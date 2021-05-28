package com.system.entity;

import javax.validation.constraints.NotNull;

/**
 * @author Luffy
 * @description
 * @createTime 2021年05月27日 14:48:00
 */
public class AccountDTO {


    /**
    * <p>当前页<p>
    */
    private Integer page;

    /**
    * <p>分页大小<p>
    */
    private Integer size;

    /**
     * <p>用户名<p>
     */
    private String username;

    /**
     * <p>年龄<p>
     */
    private Integer age;

    /**
    * <p>地址<p>
    */
    private String address;

    /**
    * <p>角色描述<p>
    */
    private String roleDescription;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
