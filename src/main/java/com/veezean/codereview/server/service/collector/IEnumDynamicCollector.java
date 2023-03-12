package com.veezean.codereview.server.service.collector;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/12
 */
public interface IEnumDynamicCollector {
    String name();
    List<String> doCollect();
    @PostConstruct
    default void init() {
        EnumDynamicCollectorManage.registeCollector(this);
    }
}
