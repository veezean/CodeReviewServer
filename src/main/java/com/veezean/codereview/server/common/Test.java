package com.veezean.codereview.server.common;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2021/4/27
 */
public class Test {

    public static void main(String[] args) {
        TestBeanb testBeanb = new TestBeanb();
//        testBeanb.setCurrentTime(22);
//        testBeanb.setId("");
//        testBeanb.setName("asdasd");
        BeanMap.create(testBeanb).entrySet().stream().filter(Objects::nonNull)
                .map(o -> o).forEach(o -> {
            System.out.println(o);
        });

        Map<Object, Object> map = new HashMap<>();
        BeanMap.create(testBeanb).forEach((key, value) -> {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
                return;
            }
            map.put(key, value);
        });
        System.out.println(map);

    }

}
