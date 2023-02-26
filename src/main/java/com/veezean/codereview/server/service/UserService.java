package com.veezean.codereview.server.service;

import com.veezean.codereview.server.model.UserPwdCheckReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;

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

    public boolean authUserPwd(UserPwdCheckReq req) {
        EruptUser eruptUser = eruptDao.queryEntity(EruptUser.class, "account=" + req
                .getAccount());
        return eruptUserService.checkPwd(eruptUser, req.getPassword());
    }

}
