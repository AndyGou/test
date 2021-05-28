package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.dao.AccountDao;
import com.system.entity.*;
import com.system.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Luffy
 * @description 用户信息服务
 * @createTime 2021年05月11日 14:48:00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public int addAccount(Account account) {
        return accountDao.insert(account);
    }

    @Override
    public int batchAddAccount(List<Account> accountList) {
        int result = 0;
        for (Account account : accountList) {
            result += accountDao.insert(account);
        }
        return result;
    }

    @Override
    public int updateAccount(Account account) {
        return accountDao.updateById(account);
    }

    @Override
    public int batchUpdateAccount(List<Account> accountList) {
        int result = 0;
        for (Account account : accountList) {
            result += accountDao.updateById(account);
        }
        return result;
    }

    @Override
    public int deleteAccount(Long id) {
        return accountDao.deleteById(id);
    }

    @Override
    public int batchDeleteAccount(List<Long> ids) {
        return accountDao.deleteBatchIds(ids);
    }

    @Override
    public ResponseMessage selectAccountList(Integer page, Integer size, AccountDTO accountDTO) {
        Page<Account> pageInfo = new Page<>(page,size);
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(accountDTO.getAge())) {
            wrapper.eq("age",accountDTO.getAge());
        }
        if (StringUtils.isNotBlank(accountDTO.getUsername())) {
            wrapper.like("username",accountDTO.getUsername());
        }
        if (StringUtils.isNotBlank(accountDTO.getAddress())) {
            wrapper.like("address",accountDTO.getAddress());
        }
        if (StringUtils.isNotBlank(accountDTO.getRoleDescription())) {
            wrapper.like("role_description",accountDTO.getRoleDescription());
        }
        IPage<Account> accountIPage = accountDao.selectPage(pageInfo, wrapper);
        long accountIPageTotal = accountIPage.getTotal();
        if (accountIPageTotal <= 0) {
            return ResponseMessage.success().data("total",0).data("list",new ArrayList<>());
        }
        List<Account> scmDataList = accountIPage.getRecords();
        List<AccountVO> result = new ArrayList<>();
        for (Account scmData : scmDataList) {
            result.add(AccountVO.from(scmData));
        }
        return ResponseMessage.success().data("total",accountIPageTotal).data("list",result);
    }
}
