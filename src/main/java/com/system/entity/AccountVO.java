package com.system.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableName;
import com.system.util.DateUtil;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@TableName("account")
public class AccountVO implements Serializable {

    /**
    * <p>主键id<p>
    */
    private Long id;

    /**
    * <p>登录名<p>
    */
    private String username;

    /**
    * <p>登录密码<p>
    */
    private String password;

    /**
    * <p>昵称<p>
    */
    private String nickName;

    /**
    * <p>年龄<p>
    */
    private Integer age;

    /**
    * <p>地址<p>
    */
    private String address;

    /**
    * <p>用户角色Code<p>
    */
    private Integer roleCode;

    /**
    * <p>用户角色描述<p>
    */
    private String roleDescription;

    /**
    * <p>添加人id<p>
    */
    private Long addAccountId;

    
    /**
    * <p>修改时间<p>
    */
    private String updateTime;

	private static final long serialVersionUID = 1L;


    public Long getAddAccountId() {
        return addAccountId;
    }

    public void setAddAccountId(Long addAccountId) {
        this.addAccountId = addAccountId;
    }
    
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public static AccountVO from(Account account) {
        AccountVO result = new AccountVO();
        BeanUtils.copyProperties(account,result,"updateTime");
        result.setUpdateTime(DateUtil.timeStampToStr(account.getUpdateTime()));
        return result;
    }
    
    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.IgnoreNonFieldGetter);
    }
}