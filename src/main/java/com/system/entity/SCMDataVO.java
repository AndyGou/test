package com.system.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.system.util.DateUtil;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Luffy
 * @description SCM前端接收数据
 * @createTime 2021年05月08日 09:28:00
 */
public class SCMDataVO implements Serializable {

    /**
    * <p>主键id<p>
    */
    private Long id;

    /**
    * <p>温度<p>
    */
    private BigDecimal temperature;

    /**
    * <p>烟雾浓度<p>
    */
    private BigDecimal concentration;

    /**
     * <p>光照强度<p>
     */
    private BigDecimal intensity;

    /**
    * <p>添加时间<p>
    */
    private String saveTime;

    /**
    * <p>修改时间<p>
    */
    private String updateTime;

    /**
    * <p>修改人Id<p>
    */
    private Long updateAccountId;

    private static final long serialVersionUID = 1L;

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getConcentration() {
        return concentration;
    }

    public void setConcentration(BigDecimal concentration) {
        this.concentration = concentration;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateAccountId() {
        return updateAccountId;
    }

    public void setUpdateAccountId(Long updateAccountId) {
        this.updateAccountId = updateAccountId;
    }

    public BigDecimal getIntensity() {
        return intensity;
    }

    public void setIntensity(BigDecimal intensity) {
        this.intensity = intensity;
    }


    public static SCMDataVO from(SCMData scmData) {
        SCMDataVO result = new SCMDataVO();
        BeanUtils.copyProperties(scmData,result,"saveTime","updateTime");
        result.setSaveTime(DateUtil.timeStampToStr(scmData.getSaveTime()));
        result.setUpdateTime(DateUtil.timeStampToStr(scmData.getUpdateTime()));
        return result;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.IgnoreNonFieldGetter);
    }
}
