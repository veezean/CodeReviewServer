package com.veezean.codereview.server.notice;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
