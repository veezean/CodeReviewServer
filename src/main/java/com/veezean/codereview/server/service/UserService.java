package com.veezean.codereview.server.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import com.veezean.codereview.server.common.*;
import com.veezean.codereview.server.entity.DepartmentEntity;
import com.veezean.codereview.server.entity.RoleEntity;
import com.veezean.codereview.server.entity.UserEntity;
import com.veezean.codereview.server.entity.UserLoginTokenEntity;
import com.veezean.codereview.server.model.*;
import com.veezean.codereview.server.monogo.MongoOperateHelper;
import com.veezean.codereview.server.repository.UserLoginTokenRepository;
import com.veezean.codereview.server.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2021/4/26
 */
@Service
@Slf4j
public class UserService {

    @Value("${application.config.tokenExpireDays:2}")
    private int tokenExpireDays;

    @Autowired
    private UserLoginTokenRepository userLoginTokenRepository;
    //    @Autowired
//    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    //    @Autowired
//    private DepartmentRepository departmentRepository;
    @Resource
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;
    @Resource
    private VersionMatchChecker versionMatchChecker;
    @Resource
    private MongoOperateHelper mongoOperateHelper;

    @Transactional
    public void createUser(SaveUserReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getAccount())
                || StringUtils.isEmpty(reqBody.getName())
                || reqBody.getDepartmentId() <= 0
                || CollectionUtils.isEmpty(reqBody.getRoles())
        ) {
            throw new CodeReviewException("请求参数有缺失");
        }

        UserEntity existUser = userRepository.findFirstByAccount(reqBody.getAccount());
        if (existUser != null) {
            throw new CodeReviewException("账号已存在");
        }

        DepartmentEntity departmentEntity = departmentService.getByDeptId(reqBody.getDepartmentId());

        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(reqBody.getAccount());
        userEntity.setName(reqBody.getName());
        userEntity.setPhoneNumber(reqBody.getPhoneNumber());
        // 创建用户默认密码，首次使用时修改
        userEntity.setPassword(MD5.create().digestHex("123456", StandardCharsets.UTF_8));
        userEntity.setDepartmentId(departmentEntity.getId());
        userEntity.setRoles(reqBody.getRoles());
        userRepository.save(userEntity);
//        roleService.bindRole(reqBody.getAccount(), reqBody.getRoles());
    }

    @Transactional
    public void modifyUser(EditUserReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getAccount())
                || StringUtils.isEmpty(reqBody.getName())
                || reqBody.getDepartmentId() <= 0
                || CollectionUtils.isEmpty(reqBody.getRoles())
        ) {
            throw new CodeReviewException("请求参数不合法");
        }

        UserEntity existUser = userRepository.findFirstByAccount(reqBody.getAccount());
        if (existUser == null) {
            throw new CodeReviewException("用户不存在");
        }
        DepartmentEntity departmentEntity = departmentService.getByDeptId(reqBody.getDepartmentId());
        existUser.setName(reqBody.getName());
        existUser.setPhoneNumber(reqBody.getPhoneNumber());
        existUser.setDepartmentId(departmentEntity.getId());
        existUser.setRoles(reqBody.getRoles());
        userRepository.save(existUser);
//        roleService.bindRole(reqBody.getAccount(), reqBody.getRoles());
    }


    @Transactional
    public void editBaseInfo(EditUserBaseInfoReqBody reqBody) {
        if (reqBody == null || StringUtils.isEmpty(reqBody.getName())
        ) {
            throw new CodeReviewException("请求参数不合法");
        }

        UserEntity existUser = userRepository.findFirstByAccount(CurrentUserHolder.getCurrentUser().getAccount());
        if (existUser == null) {
            throw new CodeReviewException("用户不存在");
        }
        existUser.setName(reqBody.getName());
        existUser.setPhoneNumber(reqBody.getPhoneNumber());
        userRepository.save(existUser);
    }

    public void modifyPassword(ChangePwdReqBody reqBody) {
        if (reqBody == null
                || StringUtils.isEmpty(reqBody.getOriginalPwd())
                || StringUtils.isEmpty(reqBody.getNewPwd())
        ) {
            throw new CodeReviewException("请求参数不合法");
        }
        UserEntity existUser = userRepository.findFirstByAccount(CurrentUserHolder.getCurrentUser().getAccount());
        if (existUser == null) {
            throw new CodeReviewException("用户不存在");
        }
        if (!StringUtils.equals(existUser.getPassword(), reqBody.getOriginalPwd())) {
            throw new CodeReviewException("修改失败，原密码不正确");
        }
        existUser.setPassword(reqBody.getNewPwd());
        userRepository.save(existUser);
    }

    @Transactional
    public void deleteUser(String account) {
        userRepository.deleteAllByAccount(account);
    }

    @Transactional
    public void deleteUsers(List<String> accounts) {
        userRepository.deleteAllByAccountIn(accounts);
    }

    public UserDetail getUserDetail(String account) {
        UserEntity userEntity = userRepository.findFirstByAccount(account);
        return convertUserEntityToUserDetail(userEntity, false, deptId -> departmentService.getByDeptId(deptId));
    }

    public PageBeanList<UserDetail> getUserDetails(PageQueryRequest<UserQueryBody> request) {
//        Pageable pageable = PageUtil.buildPageable(request);

        Criteria criteria = Criteria.where("id").gte(0L);
        UserQueryBody queryParams = request.getQueryParams();
        if (queryParams != null) {
            if (StringUtils.isNotEmpty(queryParams.getName())) {
                criteria = criteria.and("name").is(queryParams.getName());
            }
            if (StringUtils.isNotEmpty(queryParams.getPhoneNumber())) {
                criteria = criteria.and("phoneNumber").is(queryParams.getPhoneNumber());
            }
            if (StringUtils.isNotEmpty(queryParams.getAccount())) {
                criteria = criteria.and("account").is(queryParams.getPhoneNumber());
            }
            if (queryParams.getDeptId() != null) {
                criteria = criteria.and("departmentId").is(queryParams.getDeptId());
            }
        }

        Map<Long, DepartmentEntity> allDepts = departmentService.queryAllDepts();
        return mongoOperateHelper.pageQuery(
                new Query(criteria),
                UserEntity.class,
                request.getPageIndex(),
                request.getPageSize(),
                entity -> convertUserEntityToUserDetail(entity, false, allDepts::get));

//        UserEntity sampleEntity = new UserEntity();
//        if (queryParams != null) {
//            if (StringUtils.isNotEmpty(queryParams.getName())) {
//                sampleEntity.setName(queryParams.getName());
//            }
//            if (StringUtils.isNotEmpty(queryParams.getPhoneNumber())) {
//                sampleEntity.setPhoneNumber(queryParams.getPhoneNumber());
//            }
//            if (StringUtils.isNotEmpty(queryParams.getAccount())) {
//                sampleEntity.setAccount(queryParams.getAccount());
//            }
//            if (queryParams.getDeptId() != null) {
//                sampleEntity.setDepartmentId(queryParams.getDeptId());
//            }
//        }
//        Page<UserDetail> userDetails =
//                userRepository.findAll(Example.of(sampleEntity), pageable)
//                        .map(userEntity -> convertUserEntityToUserDetail(userEntity, false));
//        return PageBeanList.create(userDetails, pageable);
    }


    public List<UserDetail> getAllUserDetails() {
        Map<Long, DepartmentEntity> allDepts = departmentService.queryAllDepts();
        return userRepository.findAll().stream()
                .map(userEntity -> convertUserEntityToUserDetail(userEntity, true, allDepts::get))
                .collect(Collectors.toList());
    }

//    public static UserDetail getUserDetailByAccout(String account) {
//        return userService.getUserDetail(account);
//    }

    public UserPwdCheckRespBody authUserPwd(UserPwdCheckReq reqBody) {
        UserEntity entity = Optional.ofNullable(reqBody)
                .filter(req -> StringUtils.isNotEmpty(reqBody.getAccount()))
                .filter(req -> StringUtils.isNotEmpty(reqBody.getPassword()))
                .map(req -> userRepository.findFirstByAccount(req.getAccount()))
                .filter(userEntity -> StringUtils.equals(reqBody.getPassword(), userEntity.getPassword()))
                .orElse(null);
        UserPwdCheckRespBody respBody = new UserPwdCheckRespBody();
        if (entity == null) {
            respBody.setPass(false);
        } else {
            respBody.setPass(true);
            respBody.setUserInfo(new ValuePair(entity.getAccount(), entity.getName()));
        }
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
        UserLoginTokenEntity tokenEntity =
                Optional.ofNullable(userLoginTokenRepository.queryFirstByUserId(userEntity.getId()))
                        .orElseGet(() -> {
                            UserLoginTokenEntity entity = new UserLoginTokenEntity();
                            entity.setToken(RandomUtil.randomString(32));
                            entity.setUserId(userEntity.getId());
                            return entity;
                        });
        // 设定过期时间，如果已存在，则续期
        tokenEntity.setExpireAt(calculateTokenExpireAt());
        userLoginTokenRepository.save(tokenEntity);

        // 生层登录成功响应数据
        LoginSuccRespBody respBody = new LoginSuccRespBody();
        respBody.setUserDetail(convertUserEntityToUserDetail(userEntity, false,
                deptId -> departmentService.getByDeptId(deptId)));
        respBody.setToken(tokenEntity.getToken());
        respBody.setExpireAt(tokenEntity.getExpireAt());
        respBody.setVersion(versionMatchChecker.currentVersion());
        respBody.setVersionDescUrl(versionMatchChecker.getLatestDescUrl());
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
                .map(UserDetail::getAccount)
                .map(account -> userRepository.findFirstByAccount(account))
                .ifPresent(user -> userLoginTokenRepository.deleteAllByUserId(user.getId()));
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
                .map(UserLoginTokenEntity::getUserId)
                .map(userId -> userRepository.findById(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userEntity -> convertUserEntityToUserDetail(
                        userEntity,
                        false,
                        deptId -> departmentService.getByDeptId(deptId)
                ))
                .orElseThrow(() -> new CodeReviewException("token不合法"));
    }

    private UserDetail convertUserEntityToUserDetail(UserEntity user, boolean onlyAccountName, Function<Long,
            DepartmentEntity> convertDeptFunc) {
        UserDetail userDetail = new UserDetail();
        userDetail.setAccount(user.getAccount());
        userDetail.setName(user.getName());
        if (!onlyAccountName) {
            userDetail.setPhoneNumber(user.getPhoneNumber());
            userDetail.setDepartment(convertDeptFunc.apply(user.getDepartmentId()));
            List<RoleEntity> roleEntities = roleService.getRoleByIds(user.getRoles());
            userDetail.setRoles(roleEntities);
        }
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

        Map<Long, DepartmentEntity> depts = departmentService.queryAllDepts();

        return Optional.ofNullable(userEntities)
                .orElse(new ArrayList<>())
                .stream()
                .map(entity -> UserShortInfo.from(entity, depts.get(entity.getDepartmentId())))
                .collect(Collectors.toList());
    }
}
