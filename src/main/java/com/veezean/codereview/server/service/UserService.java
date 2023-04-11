package com.veezean.codereview.server.service;

import cn.hutool.core.util.RandomUtil;
import com.veezean.codereview.server.common.*;
import com.veezean.codereview.server.entity.*;
import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.repository.DepartmentRepository;
import com.veezean.codereview.server.repository.UserLoginTokenRepository;
import com.veezean.codereview.server.repository.UserRepository;
import com.veezean.codereview.server.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Value("${application.config.tokenExpireDays:2}")
    private int tokenExpireDays;

    @Autowired
    private UserLoginTokenRepository userLoginTokenRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostConstruct
    public void init() {
        userService = this;
    }

    @Transactional
    public void createUser(SaveUserReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getAccount())
                || StringUtils.isEmpty(reqBody.getName())
                || StringUtils.isEmpty(reqBody.getPhoneNumber())
                || StringUtils.isEmpty(reqBody.getPassword())
                || reqBody.getDepartmentId() <= 0
        ) {
            throw new CodeReviewException("请求参数不合法");
        }

        UserEntity existUser = userRepository.findFirstByAccount(reqBody.getAccount());
        if (existUser != null) {
            throw new CodeReviewException("账号已存在");
        }

        DepartmentEntity departmentEntity = departmentRepository.findById(reqBody.getDepartmentId()).orElse(null);
        if (departmentEntity == null) {
            throw new CodeReviewException("部门不存在");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(reqBody.getAccount());
        userEntity.setName(reqBody.getName());
        userEntity.setPhoneNumber(reqBody.getPhoneNumber());
        userEntity.setPassword(reqBody.getPassword());
        userEntity.setDepartment(departmentEntity);
        userRepository.saveAndFlush(userEntity);
    }

    @Transactional
    public void modifyUser(SaveUserReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getAccount())
                || StringUtils.isEmpty(reqBody.getName())
                || StringUtils.isEmpty(reqBody.getPhoneNumber())
                || reqBody.getDepartmentId() <= 0
        ) {
            throw new CodeReviewException("请求参数不合法");
        }

        UserEntity existUser = userRepository.findFirstByAccount(reqBody.getAccount());
        if (existUser == null) {
            throw new CodeReviewException("用户不存在");
        }
        DepartmentEntity departmentEntity = departmentRepository.findById(reqBody.getDepartmentId()).orElse(null);
        if (departmentEntity == null) {
            throw new CodeReviewException("部门不存在");
        }
        existUser.setName(reqBody.getName());
        existUser.setPhoneNumber(reqBody.getPhoneNumber());
        existUser.setDepartment(departmentEntity);
        // 修改场景，不允许修改密码
        userRepository.saveAndFlush(existUser);
    }

    public void modifyPassword(ChangePwdReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getAccount())
                || StringUtils.isEmpty(reqBody.getOriginalPwd())
                || StringUtils.isEmpty(reqBody.getNewPwd())
        ) {
            throw new CodeReviewException("请求参数不合法");
        }
        UserEntity existUser = userRepository.findFirstByAccount(reqBody.getAccount());
        if (existUser == null) {
            throw new CodeReviewException("用户不存在");
        }
        if (!StringUtils.equals(existUser.getPassword(), reqBody.getOriginalPwd())) {
            throw new CodeReviewException("修改失败，原密码不正确");
        }
        existUser.setPassword(reqBody.getNewPwd());
        userRepository.saveAndFlush(existUser);
    }

    @Transactional
    public void deleteUser(String account) {
        Optional.ofNullable(userRepository.findFirstByAccount(account))
                .ifPresent(userEntity -> {
                    userRoleRepository.deleteAllByUser(userEntity);
                    userRepository.delete(userEntity);
                });
    }

    public UserDetail getUserDetail(String account) {
        UserEntity userEntity = userRepository.findFirstByAccount(account);
        return convertUserEntityToUserDetail(userEntity);
    }

    public PageBeanList<UserDetail> getUserDetails(PageQueryRequest<UserQueryBody> request) {
        Pageable pageable = PageUtil.buildPageable(request);
        UserQueryBody queryParams = request.getQueryParams();

        UserEntity sampleEntity = new UserEntity();
        if (queryParams != null) {
            if (StringUtils.isNotEmpty(queryParams.getName())) {
                sampleEntity.setName(queryParams.getName());
            }
            if (StringUtils.isNotEmpty(queryParams.getPhoneNumber())) {
                sampleEntity.setPhoneNumber(queryParams.getPhoneNumber());
            }
            if (StringUtils.isNotEmpty(queryParams.getAccount())) {
                sampleEntity.setAccount(queryParams.getAccount());
            }
            if (queryParams.getDeptId() != null) {
                departmentRepository.findById(queryParams.getDeptId()).ifPresent(sampleEntity::setDepartment);
            }
        }
        Page<UserDetail> userDetails =
                userRepository.findAll(Example.of(sampleEntity), pageable)
                        .map(this::convertUserEntityToUserDetail);
        return PageBeanList.create(userDetails, pageable);
    }

    public static UserDetail getUserDetailByAccout(String account) {
        return userService.getUserDetail(account);
    }

    public UserPwdCheckRespBody authUserPwd(UserPwdCheckReq reqBody) {
        boolean present = Optional.ofNullable(reqBody)
                .filter(req -> StringUtils.isNotEmpty(reqBody.getAccount()))
                .filter(req -> StringUtils.isNotEmpty(reqBody.getPassword()))
                .map(req -> userRepository.findFirstByAccount(req.getAccount()))
                .filter(userEntity -> StringUtils.equals(reqBody.getPassword(), userEntity.getPassword()))
                .isPresent();
        UserPwdCheckRespBody respBody = new UserPwdCheckRespBody();
        respBody.setPass(present);
        return respBody;
    }

    @Transactional
    public LoginSuccRespBody userLogin(UserPwdCheckReq userPwdCheckReq) {
        UserEntity userEntity = Optional.ofNullable(userPwdCheckReq)
                .filter(req -> StringUtils.isNotEmpty(req.getAccount()))
                .filter(req -> StringUtils.isNotEmpty(req.getPassword()))
                .map(req -> userRepository.findFirstByAccount(req.getAccount()))
                .filter(user -> StringUtils.equals(user.getPassword(), userPwdCheckReq.getPassword()))
                .orElseThrow(() -> new CodeReviewException("登录失败,用户名或密码错误"));

        // 生层token值
        UserLoginTokenEntity tokenEntity = Optional.ofNullable(userLoginTokenRepository.queryFirstByUser(userEntity))
                .orElseGet(() -> {
                    UserLoginTokenEntity entity = new UserLoginTokenEntity();
                    entity.setToken(RandomUtil.randomString(32));
                    entity.setUser(userEntity);
                    return entity;
                });
        // 设定过期时间，如果已存在，则续期
        tokenEntity.setExpireAt(calculateTokenExpireAt());
        userLoginTokenRepository.saveAndFlush(tokenEntity);

        // 生层登录成功响应数据
        LoginSuccRespBody respBody = new LoginSuccRespBody();
        respBody.setUserDetail(convertUserEntityToUserDetail(userEntity));
        respBody.setToken(tokenEntity.getToken());
        respBody.setExpireAt(tokenEntity.getExpireAt());
        return respBody;
    }

    private long calculateTokenExpireAt() {
        return System.currentTimeMillis() + CommonConsts.ONE_DAY_MILLIS * tokenExpireDays;
    }

    /**
     * 退出登录，删除相关token缓存
     */
    @Transactional
    public void userLogout() {
        Optional.ofNullable(CurrentUserHolder.getCurrentUser())
                .ifPresent(user -> userLoginTokenRepository.deleteAllByUserAccount(user.getAccount()));
    }

    /**
     * 服务端token鉴权
     *
     * @param token
     * @return
     */
    public UserDetail authUserByToken(String token) {
        return Optional.ofNullable(token)
                .filter(StringUtils::isNotEmpty)
                .map(s -> userLoginTokenRepository.queryFirstByToken(s))
                .filter(userLoginTokenEntity -> userLoginTokenEntity.getExpireAt() > System.currentTimeMillis())
                .map(UserLoginTokenEntity::getUser)
                .map(this::convertUserEntityToUserDetail)
                .orElseThrow(() -> new CodeReviewException("token不合法"));
    }

    private UserDetail convertUserEntityToUserDetail(UserEntity user) {
        UserDetail userDetail = new UserDetail();
        userDetail.setAccount(user.getAccount());
        userDetail.setName(user.getName());
        userDetail.setPhoneNumber(user.getPhoneNumber());
        userDetail.setDepartment(user.getDepartment());
        List<RoleEntity> roleEntities = userRoleRepository.findAllByUser(user)
                .stream()
                .map(UserRoleEntity::getRole)
                .collect(Collectors.toList());
        userDetail.setRoles(roleEntities);
        return userDetail;
    }

    /**
     * 清理已过期的token
     */
    @Transactional
    public void cleanExpiredTokens() {
        userLoginTokenRepository.deleteAllByExpireAtLessThan(System.currentTimeMillis());
    }

    public UserDetail authAndGetUserInfo(String account, String pwd) {
        boolean present = Optional.ofNullable(authUserPwd(new UserPwdCheckReq(account, pwd)))
                .filter(UserPwdCheckRespBody::isPass)
                .isPresent();
        if (present) {
            return getUserDetail(account);
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
        List<UserEntity> userEntities;
        if (ClientUserQueryType.ALL.equals(queryType)) {
            userEntities = userRepository.findAll();
        } else if (ClientUserQueryType.SAME_DEPARTMENT.equals(queryType)) {
            userEntities =
                    userRepository.findAllByDepartmentId(CurrentUserHolder.getCurrentUser().getDepartment().getId());
        } else {
            throw new CodeReviewException("客户端查询用户类型不合法:" + queryType);
        }

        return Optional.ofNullable(userEntities)
                .orElse(new ArrayList<>())
                .stream()
                .map(userEntity -> {
                    UserShortInfo userShortInfo = new UserShortInfo();
                    userShortInfo.setAccount(userEntity.getAccount());
                    userShortInfo.setUserName(userEntity.getName());
                    Optional.ofNullable(userEntity.getDepartment())
                            .map(DepartmentEntity::getName)
                            .ifPresent(userShortInfo::setDepartment);
                    return userShortInfo;
                })
                .collect(Collectors.toList());
    }
}
