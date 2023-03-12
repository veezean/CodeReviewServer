package com.veezean.codereview.server.erupt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.expr.ExprBool;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/5
 */
@Component
@Slf4j
public class CommentFieldRenderHandler implements ExprBool.ExprHandler {

    @Override
    public boolean handler(boolean expr, String[] params) {
        return true;
    }
}
