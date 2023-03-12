package com.veezean.codereview.server.erupt;

import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptUser;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息下拉框内容处理器
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2022/3/19
 */
@Component
public class UserChoiceFetchHandler implements ChoiceFetchHandler {

    @Resource
    private EruptDao eruptDao;

    @Override
    public List<VLModel> fetch(String[] params) {
        return eruptDao.queryEntityList(EruptUser.class)
                .stream()
                .map(eruptUser -> new VLModel(eruptUser.getAccount(), eruptUser.getName()))
                .collect(Collectors.toList());
    }
}
