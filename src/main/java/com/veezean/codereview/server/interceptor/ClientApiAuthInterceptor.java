package com.veezean.codereview.server.interceptor;

import com.alibaba.fastjson.JSON;
import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.common.VersionMatchChecker;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.UserDetail;
import com.veezean.codereview.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/5
 */
@Component
@Slf4j
public class ClientApiAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Resource
    private VersionMatchChecker versionMatchChecker;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("----request IN, url: {}", request.getRequestURI());
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 账密校验
        boolean loginAuth = doAuth(request, response);
        if (!loginAuth) {
            return loginAuth;
        }

        // 服务端与客户端之间版本匹配兼容性检查
        return versionMatchCheck(request, response);
    }

    private boolean doAuth(HttpServletRequest request, HttpServletResponse response) {
        try {
            String account = request.getHeader("account");
            String pwd = request.getHeader("pwd");
            if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(account)) {
                throw new CodeReviewException("account或者pwd信息缺失");
            }
            UserDetail userDetail = userService.authAndGetUserInfo(account, pwd);
            CurrentUserHolder.saveCurrentUserInfo(userDetail);
            return true;
        } catch (Exception e) {
            // 校验没通过，直接终止了，清理下线程数据
            CurrentUserHolder.clearCurrentThreadCache();

            response.setStatus(401);
            Response<Object> errResp = Response.simpleFailResponse(-1, e.getMessage());
            try {
                response.getOutputStream().write(JSON.toJSONString(errResp).getBytes());
            } catch (IOException ex) {
                throw new CodeReviewException("inner error", ex);
            }
            return false;
        }
    }

    private boolean versionMatchCheck(HttpServletRequest request, HttpServletResponse response) {
        try {
            String clientVersion = request.getHeader("version");
            VersionMatchChecker.VersionCheckResult checkResult = versionMatchChecker.versionCheck(clientVersion);
            if (checkResult.isMatch()) {
                return true;
            } else {
                log.error("client version check failed:{}", checkResult);
                // 校验没通过，直接终止了，清理下线程数据
                CurrentUserHolder.clearCurrentThreadCache();

                response.setStatus(403);
                Response<Object> errResp = Response.simpleFailResponse(-1, checkResult.getNotice());
                try {
                    response.getOutputStream().write(JSON.toJSONString(errResp).getBytes());
                } catch (IOException ex) {
                    throw new CodeReviewException("version check error", ex);
                }
                return false;
            }
        } catch (Exception e) {
            log.warn("client version check error", e);
        }
        // 未知情况，统一按照通过处理，避免误杀
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        log.info("----request OUT, url: {}", request.getRequestURI());
        CurrentUserHolder.clearCurrentThreadCache();
    }
}
