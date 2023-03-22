package com.veezean.codereview.server.service;

import com.veezean.codereview.server.common.ClientUserQueryType;
import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.model.ClientUserQueryReqBody;
import com.veezean.codereview.server.model.UserPwdCheckReq;
import com.veezean.codereview.server.model.UserPwdCheckRespBody;
import com.veezean.codereview.server.model.UserShortInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptOrg;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2021/4/26
 */
@Service
@Slf4j
public class UserService {

    private static UserService userService;

    @Resource
    private EruptDao eruptDao;

    @Autowired
    private EruptUserService eruptUserService;

    @PostConstruct
    public void init() {
        userService = this;
    }

    public static EruptUser getUserDetailByAccout(String account) {
        return userService.getUserByAccount(account);
    }

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

    public List<UserShortInfo> getUserShortInfoList() {
        ClientUserQueryReqBody reqBody = new ClientUserQueryReqBody();
        return getUserShortInfoList(reqBody);
    }
    /**
     * 拉取系统内所有的用户信息
     *
     * @return
     */
    private List<UserShortInfo> getUserShortInfoList(ClientUserQueryReqBody reqBody) {
        ClientUserQueryType queryType = Optional.ofNullable(reqBody).map(ClientUserQueryReqBody::getQueryType)
                .map(ClientUserQueryType::getType)
                .orElse(ClientUserQueryType.ALL);
        List<EruptUser> eruptUsers;
        if (ClientUserQueryType.ALL.equals(queryType)) {
            eruptUsers = eruptDao.queryEntityList(EruptUser.class);
        } else if (ClientUserQueryType.SAME_DEPARTMENT.equals(queryType)) {
            String currentUserOrgCode = Optional.ofNullable(CurrentUserHolder.getCurrentUser())
                    .map(EruptUser::getEruptOrg)
                    .map(EruptOrg::getCode)
                    .<CodeReviewException>orElseThrow(() -> new CodeReviewException("获取当前用户归属部门失败"));
            Map<String, Object> params = new HashMap<>();
            params.put("orgCode", currentUserOrgCode);
            eruptUsers = eruptDao.queryEntityList(EruptUser.class, "eruptOrg.code = :orgCode", params);
        } else {
            throw new CodeReviewException("客户端查询用户类型不合法:" + queryType);
        }

        return Optional.ofNullable(eruptUsers)
                .orElse(new ArrayList<>())
                .stream()
                .map(eruptUser -> {
                    UserShortInfo userShortInfo = new UserShortInfo();
                    userShortInfo.setAccount(eruptUser.getAccount());
                    userShortInfo.setUserName(eruptUser.getName());
                    Optional.ofNullable(eruptUser.getEruptOrg())
                            .map(EruptOrg::getCode)
                            .ifPresent(userShortInfo::setDepartment);
                    return userShortInfo;
                })
                .collect(Collectors.toList());
    }
}
