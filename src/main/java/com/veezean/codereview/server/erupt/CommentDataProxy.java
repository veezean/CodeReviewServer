package com.veezean.codereview.server.erupt;

import cn.hutool.core.util.RandomUtil;
import com.veezean.codereview.server.entity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.query.Condition;
import xyz.erupt.upms.service.EruptUserService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2022/3/20
 */
@Component
public class CommentDataProxy implements DataProxy<CommentEntity> {

    @Autowired
    private EruptUserService eruptUserService;

    @Override
    public void beforeAdd(CommentEntity commentEntity) {
        // 点击新增按钮提交请求后的预处理操作

        // 当前时间作为评审记录添加时间
        commentEntity.setReviewTime(new Date());
    }

    @Override
    public void afterAdd(CommentEntity commentEntity) {
        // 新增操作执行完成之后的操作

    }

    @Override
    public void beforeUpdate(CommentEntity commentEntity) {

    }

    @Override
    public void afterUpdate(CommentEntity commentEntity) {

    }

    @Override
    public void beforeDelete(CommentEntity commentEntity) {

    }

    @Override
    public void afterDelete(CommentEntity commentEntity) {

    }

    @Override
    public String beforeFetch(List<Condition> conditions) {
        return null;
    }

    @Override
    public void afterFetch(Collection<Map<String, Object>> list) {

    }

    @Override
    public void addBehavior(CommentEntity commentEntity) {
        // 新增窗口打开的时候的初始化操作

        String currentAccount = eruptUserService.getCurrentEruptUser().getAccount();
        commentEntity.setCommitUser(currentAccount);
        commentEntity.setIdentifier(RandomUtil.randomLong());
    }

    @Override
    public void editBehavior(CommentEntity commentEntity) {

    }
}
