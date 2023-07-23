package com.veezean.codereview.server.service.collector;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/12
 */
@Slf4j
public class EnumDynamicCollectorManage {
    private static Map<String, IEnumDynamicCollector> enumDynamicCollectorMap = new ConcurrentHashMap<>();

    public static void registeCollector(IEnumDynamicCollector collector) {
        enumDynamicCollectorMap.put(collector.name(), collector);
        log.info("动态枚举处理器注册完成，name: {}", collector.name());
    }

    public static Optional<IEnumDynamicCollector> getCollector(String name) {
        return Optional.ofNullable(enumDynamicCollectorMap.get(name));
    }
}
