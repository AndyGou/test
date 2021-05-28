package com.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.entity.SCMData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



/**
 * @author Luffy
 * @description scm数据库访问层
 * @createTime 2021年05月08日 10:02:00
 */
@Repository
@Mapper
public interface SCMDao extends BaseMapper<SCMData> {


}
