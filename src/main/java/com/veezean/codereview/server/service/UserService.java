package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.model.UserPwdCheckReq;
import com.veezean.codereview.server.model.UserPwdCheckRespBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/4/26
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private EruptDao eruptDao;

    @Autowired
    private EruptUserService eruptUserService;

    private EruptUser getUserByAccount(String account) {
        return eruptDao.queryEntity(EruptUser.class, "account=:account", new HashMap<String, Object>(1) {{
            this.put("account", account);
        }});
    }

    public UserPwdCheckRespBody authUserPwd(UserPwdCheckReq req) {
        EruptUser eruptUser = getUserByAccount(req.getAccount());
        boolean checkPwd = eruptUserService.checkPwd(eruptUser, req.getPassword());
        return new UserPwdCheckRespBody(checkPwd);
    }

    public EruptUser authAndGetUserInfo(String account, String pwd) {
        EruptUser eruptUser = getUserByAccount(account);

        boolean checkPwd = StringUtils.equals(pwd, eruptUser.getPassword());
        if (checkPwd) {
            return eruptUser;
        }
        throw new CodeReviewException("鉴权信息不正确");
    }

}
