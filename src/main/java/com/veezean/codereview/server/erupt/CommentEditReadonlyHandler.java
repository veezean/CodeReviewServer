package com.veezean.codereview.server.erupt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.sub_field.Readonly;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/5
 */
@Component
@Slf4j
public class CommentEditReadonlyHandler implements Readonly.ReadonlyHandler {

    @Override
    public boolean add(boolean add, String[] params) {
        return false;
    }

    @Override
    public boolean edit(boolean edit, String[] params) {
        return false;
    }
}
