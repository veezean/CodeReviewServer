package com.veezean.codereview.server.service.collector;

import com.veezean.codereview.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/12
 */
@Component
public class UserEnumDynamicCollector implements IEnumDynamicCollector {
    private static final String SERVER_DYNAMIC_USERLIST = "UserList";

    @Autowired
    private UserService userService;

    @Override
    public String name() {
        return SERVER_DYNAMIC_USERLIST;
    }

    @Override
    public List<String> doCollect() {
        return userService.getUserShortInfoList()
                .stream()
                .map(userShortInfo -> userShortInfo.getUserName() + "|" + userShortInfo.getAccount())
                .collect(Collectors.toList());
    }
}
