package com.system.enumeration;

/**
 * @author Luffy
 * @description 错误信息枚举
 * @createTime 2021年05月08日 12:27:00
 */
public enum ErrorEnum {

    IO_ERROR("IO Error"),
    SELECT_ERROR("Select Data Error."),
    UPDATE_ERROR("Update Data Error."),
    SAVE_ERROR("Save Data Error."),
    DELETE_ERROR("Delete Data Error."),
    SCM_ID_IS_NULL("SCMData Id Is Null."),
    All_SCM_ID_IS_NULL("All SCMData Id Is Null."),
    TARGET_SCM_LIST_IS_NULL("Target ScmData List Is Null.");

    private String errorMessage;

    ErrorEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
