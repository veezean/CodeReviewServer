package com.veezean.codereview.server.interceptor;

import com.alibaba.fastjson.JSON;
import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.CurrentUserHolder;
import com.veezean.codereview.server.model.Response;
import com.veezean.codereview.server.model.UserDetail;
import com.veezean.codereview.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/5
 */
@Component
@Slf4j
public class ClientApiAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("----request IN, url: {}", request.getRequestURI());
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
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


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("----request OUT, url: {}", request.getRequestURI());
        CurrentUserHolder.clearCurrentThreadCache();
    }
}
