package com.veezean.codereview.server.notice;

import cn.hutool.core.map.MapUtil;
import com.sun.org.apache.regexp.internal.RE;
import com.veezean.codereview.server.monogo.CommentNoticeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.ScopeNotActiveException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/7/2
 */
@Slf4j
public class NoticeCache {
    private static ConcurrentHashMap<String, List<CommentNoticeEvent>> events = new ConcurrentHashMap<>();

    private NoticeCache() {
    }

    public static void addNoticeEvent(CommentNoticeEvent event) {
        log.info("receive notice event:{}", event);
        event.findReceiver().ifPresent(receiver -> {
            events.computeIfAbsent(receiver, k -> new ArrayList<>()).add(event);
        });
    }

    public static Map<String, List<CommentNoticeEvent>> getNoticeEvents() {
        return events;
    }
}
