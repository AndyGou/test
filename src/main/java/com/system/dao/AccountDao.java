package com.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author Luffy
 * @description 账户数据库访问层
 * @createTime 2021年05月08日 11:11:00
 */
@Repository
@Mapper
public interface AccountDao extends BaseMapper<Account> {


}
