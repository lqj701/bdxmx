package com.ik.service.miniprogram.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.extend.generic.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.ik.crm.commons.util.BCryptUtil;
import com.ik.service.miniprogram.constants.ErrorCode;
import com.ik.service.miniprogram.mapper.AccountMapper;
import com.ik.service.miniprogram.model.Account;
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.service.AccountService;
import com.ik.service.miniprogram.service.TeacherService;

/**
 * ServiceImpl: AccountServiceImpl
 * Mapper : AccountMapper
 * Model  : Account
 *
 * This ServiceImpl generated by MyBatis Generator Extend at 2018-12-04 13:49:32
 */
@Service
@Transactional
public class AccountServiceImpl extends GenericServiceImpl<Account, Integer, AccountMapper> implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountMapper getGenericMapper() {
        return accountMapper;
    }

    @Autowired
    private AccountService accountService;

    @Autowired
    private TeacherService teacherService;

    @Override
    public List<Account> getAccount(String phone) {
        Account account = new Account();
        account.setPhone(phone);

        return accountMapper.select(account);
    }

    @Override
    public Account register(String phone, String password, String name, String email, String avatarUrl,
            Boolean gendar) {
        Account account = new Account();
        account.setUsable(true);
        account.setPhone(phone);
        account.setName(name);
        account.setEmail(email);
        account.setEncryptedPassword(BCryptUtil.digest(password));
        account.setAvatarUrl(avatarUrl);
        account.setGender(gendar);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        accountMapper.insertSelective(account);

        return account;
    }

    @Override
    public JSONObject updateAccount(Integer accountId, String name, String email, String avatarUrl, Boolean gendar,
            Integer type) {
        JSONObject data = new JSONObject();
        Account account = new Account();
        account.setId(accountId);
        account = accountMapper.selectByPrimaryKey(accountId);
        if (account.getUsable()) {
            data.put("code", ErrorCode.ACCOUNT_NOT_USABLE.getCode());
            return data;
        }
        account.setName(name);
        account.setEmail(email);
        account.setAvatarUrl(avatarUrl);
        account.setGender(gendar);
        accountMapper.updateByPrimaryKeySelective(account);

        Teacher teacher = teacherService.update(account, type);
        data.put("teacher", teacher);

        return data;
    }

    @Override
    public JSONObject updatePassword(Integer accountId, String password, String passwordNew) {
        JSONObject data = new JSONObject();
        Account account = new Account();
        account.setId(accountId);
        account = accountMapper.selectByPrimaryKey(accountId);
        if (null == account) {
            data.put("error", ErrorCode.ACCOUNT_NOT_EXIST);
            return data;
        }
        if (!account.getUsable()) {
            data.put("error", ErrorCode.ACCOUNT_NOT_USABLE);
            return data;
        }
        if (StringUtils.isEmpty(account.getEncryptedPassword())
                || !BCryptUtil.verifyPassword(account.getEncryptedPassword(), password)) {
            data.put("error", ErrorCode.PASSWORD_WRONG);
            return data;
        }
        account.setEncryptedPassword(BCryptUtil.digest(passwordNew));
        account.setUpdatedAt(new Date());
        accountMapper.updateByPrimaryKeySelective(account);

        data.put("account", account);
        return data;
    }

    @Override
    public JSONObject loginVerify(String phone, String password) {
        JSONObject data = new JSONObject();
        List<Account> accountList = accountService.getAccount(phone);
        if (CollectionUtils.isEmpty(accountList)) {
            data.put("error", ErrorCode.ACCOUNT_NOT_EXIST);
            return data;
        }
        if (accountList.size() > 1) {
            data.put("error", ErrorCode.ACCOUNT_NOT_ONLY);
            return data;
        }
        if (!accountList.get(0).getUsable()) {
            data.put("error", ErrorCode.ACCOUNT_NOT_USABLE);
            return data;
        }
        List<Teacher> teacherList = teacherService.getTeachersByAccountId(accountList.get(0).getId());

        if (teacherList.size() > 1) {
            data.put("error", ErrorCode.LOGIN_FAILED);
        }

        if (StringUtils.isEmpty(accountList.get(0).getEncryptedPassword())
                || !BCryptUtil.verifyPassword(accountList.get(0).getEncryptedPassword(), password)) {
            data.put("error", ErrorCode.PASSWORD_WRONG);
            return data;
        }
        data.put("code", 0);
        data.put("teacher", teacherList.get(0));
        data.put("account", accountList.get(0));
        return data;
    }
}