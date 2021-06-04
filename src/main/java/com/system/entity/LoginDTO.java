package com.system.entity;

import javax.validation.constraints.NotNull;

/**
 * @author Luffy
 * @description 登录信息
 * @createTime 2021年05月26日 19:44:00
 */
public class LoginDTO {

    @NotNull(message = "username")
    private String username;

    @NotNull(message = "password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
