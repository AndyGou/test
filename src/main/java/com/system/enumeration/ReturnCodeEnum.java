package com.system.enumeration;

/**
 * @author Luffy
 * @description 返回code枚举
 * @createTime 2021年05月08日 09:35:00
 */
public enum ReturnCodeEnum {

    ERROR(20001,"error"),
    SUCCESS(20000,"success");

    private Integer code;

    private String description;

    ReturnCodeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
