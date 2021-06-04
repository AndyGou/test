package com.system.service;

import com.system.entity.Account;
import com.system.entity.AccountDTO;
import com.system.entity.LoginDTO;
import com.system.entity.ResponseMessage;

import java.util.List;

/**
 * @author Luffy
 * @description 用户信息服务层
 * @createTime 2021年05月11日 14:48:00
 */
public interface AccountService {

    /**
     * <p>添加账号<p>
     */
    int addAccount(Account account);

    /**
     * <p>批量添加账号<p>
     */
    int batchAddAccount(List<Account> accountList);

    /**
     * <p>修改账号<p>
     */
    int updateAccount(Account account);

    /**
     * <p>批量修改账号<p>
     */
    int batchUpdateAccount(List<Account> accountList);

    /**
     * <p>删除账号<p>
     */
    int deleteAccount(Long id);

    /**
     * <p>批量删除账号<p>
     */
    int batchDeleteAccount(List<Long> ids);

    /**
     * <p>分页查询账号信息<p>
     */
    ResponseMessage selectAccountList(Integer start, Integer size, AccountDTO accountDTO);

    String login(LoginDTO loginDTO);

}
