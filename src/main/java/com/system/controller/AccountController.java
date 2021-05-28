package com.system.controller;

import com.alibaba.fastjson.JSON;
import com.system.entity.Account;
import com.system.entity.AccountDTO;
import com.system.entity.LoginDTO;
import com.system.entity.ResponseMessage;
import com.system.enumeration.ErrorEnum;
import com.system.service.AccountService;
import com.system.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author Luffy
 * @description 账户控制层
 * @createTime 2021年05月08日 11:10:00
 */
@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/login")
    public ResponseMessage login(@RequestBody LoginDTO loginDTO) {
        LOGGER.info("Login info is {}",loginDTO);
        return ResponseMessage.success().data("token","admin");
    }

    @GetMapping("/info")
    public ResponseMessage info() {

        return ResponseMessage.success().data("roles","[admin]").data("name","admin").
                data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");

    }

    @PutMapping("/save")
    public ResponseMessage saveAccount(@RequestBody Account account) {
        Long currentTimestamp = DateUtil.getCurrentTimestamp();
        populateAccount(account,currentTimestamp);
        int affectRow = 0;
        try {
            affectRow = accountService.addAccount(account);
        } catch (Exception e) {
            LOGGER.error("[AccountController > saveAccount] Save account fail. Target account is {} . Track",account,e);
        }
        return checkResult(affectRow, ErrorEnum.SAVE_ERROR);
    }

    @PutMapping("/batchSave")
    public ResponseMessage batchSaveAccount(@Valid @RequestBody List<Account> accountList) {
        Long currentTimestamp = DateUtil.getCurrentTimestamp();
        accountList.forEach(scmData -> populateAccount(scmData,currentTimestamp));
        int affectRow = 0;
        try {
            affectRow = accountService.batchAddAccount(accountList);
        } catch (Exception e) {
            LOGGER.error("[AccountController > batchSaveAccount] Batch save account fail. Target accountList is {} . Track", JSON.toJSONString(accountList),e);
        }
        return checkResult(affectRow,ErrorEnum.SAVE_ERROR);
    }

    private void populateAccount(Account account, Long timeStamp) {
        if (account.getRoleCode().equals(1)) {
            account.setRoleDescription("管理员");
        } else {
            account.setRoleDescription("普通用户");
        }
        account.setAddTime(timeStamp);
        account.setUpdateTime(timeStamp);
    }

    public ResponseMessage checkResult(int affectRow,ErrorEnum type) {
        return affectRow <= 0 ? ResponseMessage.error().data("errorMessage",type.getErrorMessage()) : ResponseMessage.success();
    }

    @PostMapping("/list")
    public ResponseMessage getAccountList(@RequestBody AccountDTO accountDTO) {

        Integer page = accountDTO.getPage();
        Integer size = accountDTO.getSize();
        if (Objects.isNull(page) || page <= 0) {
            page = 1;
        }

        if (Objects.isNull(size) || size <= 0) {
            size = 20;
        }

        try {
            return  accountService.selectAccountList(page,size,accountDTO);
        } catch (Exception e) {
            LOGGER.error("[AccountController > getAccountList] Select accountList fail. Track",e);
            return ResponseMessage.error().data("errorMessage", ErrorEnum.SELECT_ERROR.getErrorMessage());
        }
    }

    @DeleteMapping("/byId/{id}")
    public ResponseMessage deleteAccount(@NotNull(message = "id can not be null.") @PathVariable Long id) {
        int affectRow = 0;
        try {
            affectRow = accountService.deleteAccount(id);
        } catch (Exception e) {
            LOGGER.error("[AccountController > deleteAccount] Delete account by id fail. id is {} Track",id,e);
        }

        return checkResult(affectRow,ErrorEnum.DELETE_ERROR);
    }

    @DeleteMapping("/batchById")
    public ResponseMessage batchDeleteAccount(@NotNull(message = "ids can not be null.") @RequestParam("ids") List<Long> ids) {
        int affectRow = 0;
        try {
            affectRow = accountService.batchDeleteAccount(ids);
        } catch (Exception e) {
            LOGGER.error("[AccountController > batchDeleteAccount] Batch delete account by id fail. ids is {} Track",ids,e);
        }
        return checkResult(affectRow,ErrorEnum.DELETE_ERROR);
    }


    @PutMapping("/update/byId")
    public ResponseMessage updateAccount(@RequestBody Account account) {
        if (Objects.isNull(account.getId())) {
            LOGGER.error("[AccountController > updateAccount] Account's id is null.");
            return ResponseMessage.error().data("errorMessage",ErrorEnum.SCM_ID_IS_NULL.getErrorMessage());
        }
        Long currentTimestamp = DateUtil.getCurrentTimestamp();
        account.setUpdateTime(currentTimestamp);
        if (account.getRoleCode().equals(1)) {
            account.setRoleDescription("管理员");
        } else {
            account.setRoleDescription("普通用户");
        }

        int affectRow = 0;
        try {
            affectRow = accountService.updateAccount(account);
        } catch (Exception e) {
            LOGGER.error("[AccountController > updateAccount] Update account fail. Target account is {} Track",account,e);
        }
        return checkResult(affectRow,ErrorEnum.UPDATE_ERROR);
    }

    @PutMapping("/batchUpdate/byId")
    public ResponseMessage batchUpdateAccount(@NotNull(message = "accountList can not be null") @RequestBody List<Account> accountList) {
        checkAccountList(accountList);
        if (accountList.size() == 0) {
            LOGGER.error("[AccountController > updateAccount] All account's id is null.");
            return ResponseMessage.error().data("errorMessage",ErrorEnum.All_SCM_ID_IS_NULL.getErrorMessage());
        }
        int affectRow = 0;
        try {
            affectRow = accountService.batchUpdateAccount(accountList);
        } catch (Exception e) {
            LOGGER.error("[AccountController > batchUpdateAccount] Batch Update account fail. Target accountList is {} Track", JSON.toJSONString(accountList),e);
        }
        return checkResult(affectRow,ErrorEnum.UPDATE_ERROR);
    }

    /**
     * <p>校验accountList数据<p>
     */
    public void checkAccountList(List<Account> accountList) {
        Iterator<Account> accountIterator = accountList.iterator();
        Long currentTimestamp = DateUtil.getCurrentTimestamp();
        while (accountIterator.hasNext()) {
            Account account = accountIterator.next();
            if (Objects.isNull(account.getId())) {
                accountIterator.remove();
                continue;
            }
            account.setUpdateTime(currentTimestamp);
        }
    }
}
