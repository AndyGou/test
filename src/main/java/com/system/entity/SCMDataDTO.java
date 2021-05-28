package com.system.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Luffy
 * @description SCM分页查询接收条件
 * @createTime 2021年05月25日 15:27:00
 */
public class SCMDataDTO {

    private Integer page;

    private Integer size;

    /**
    * <p>开始时间<p>
    */
    private Long begin;

    /**
    * <p>结束时间<p>
    */
    private Long end;

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

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

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.IgnoreNonFieldGetter);
    }
}
