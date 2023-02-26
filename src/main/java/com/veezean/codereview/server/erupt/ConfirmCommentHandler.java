package com.veezean.codereview.server.erupt;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.OperationHandler;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/2/26
 */
@Component
@Slf4j
public class ConfirmCommentHandler implements OperationHandler {

    @Override
    public String exec(List data, Object o, String[] param) {
        return null;
    }
}
