package com.veezean.codereview.server.service.collector;

import com.veezean.codereview.server.model.ValuePair;
import com.veezean.codereview.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户列表信息下拉框内容生成器
 *
 * @author Veezean
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
    public List<ValuePair> doCollect() {
        return userService.getUserShortInfoList()
                .stream()
                .map(userShortInfo -> new ValuePair(userShortInfo.getAccount(), userShortInfo.getUserName()))
                .collect(Collectors.toList());
    }
}
