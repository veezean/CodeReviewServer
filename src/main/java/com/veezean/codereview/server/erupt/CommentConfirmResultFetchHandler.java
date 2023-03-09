package com.veezean.codereview.server.erupt;

import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/5
 */
@Component
public class CommentConfirmResultFetchHandler implements ChoiceFetchHandler {

    @Override
    public List<VLModel> fetch(String[] params) {
        return Stream.of(
                new VLModel("未确认", "未确认"),
                new VLModel("已修改", "已修改"),
                new VLModel("待修改", "待修改"),
                new VLModel("拒绝", "拒绝")
        ).collect(Collectors.toList());
    }
}
