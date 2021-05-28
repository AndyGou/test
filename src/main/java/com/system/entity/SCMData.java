package com.system.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Luffy
 * @description 单片机上传的数据
 * @createTime 2021年05月08日 09:28:00
 */
@TableName("scm")
public class SCMData implements Serializable {

    /**
    * <p>主键id<p>
    */
    @ExcelIgnore()
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
    * <p>温度<p>
    */
    @ExcelProperty("温度")
    @NotNull(message = "temperature can not be null.")
    private BigDecimal temperature;

    /**
    * <p>烟雾浓度<p>
    */
    @ExcelProperty("烟雾浓度")
    @NotNull(message = "concentration can not be null.")
    private BigDecimal concentration;

    /**
     * <p>光照强度<p>
     */
    @ExcelProperty("光照强度")
    @NotNull(message = "intensity can not be null.")
    private BigDecimal intensity;

    /**
    * <p>添加时间<p>
    */
    @ExcelProperty("添加时间")
    private Long saveTime;

    /**
    * <p>修改时间<p>
    */
    @ExcelProperty("修改时间")
    private Long updateTime;

    /**
    * <p>修改人Id<p>
    */
    @ExcelProperty("修改人账号ID")
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

    public Long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Long saveTime) {
        this.saveTime = saveTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
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

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.IgnoreNonFieldGetter);
    }
}
