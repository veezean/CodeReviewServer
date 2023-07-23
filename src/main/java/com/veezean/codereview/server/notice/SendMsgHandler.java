package com.veezean.codereview.server.notice;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.veezean.codereview.server.monogo.CommentNoticeEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/7/2
 */
@Component
@Slf4j
public class SendMsgHandler {
    @Value("${application.notice.webhook.url:}")
    private String noticeWebhookUrl;

    @Scheduled(fixedDelay = 60000L, initialDelay = 60000L)
    public void doSend() {
        Map<String, List<CommentNoticeEvent>> noticeEvents = NoticeCache.getNoticeEvents();
        try {
            if (noticeEvents.isEmpty()) {
                return;
            }
            if (StringUtils.isNotEmpty(noticeWebhookUrl)) {
                String postBody = JSON.toJSONString(noticeEvents);
                log.info("send to webhook:{}", postBody);
                HttpUtil.post(noticeWebhookUrl, postBody);
            }
        } catch (Exception e) {
            log.error("failed to send notices...", e);
        } finally {
            // 防止内存溢出，不管是否成功，均删除
            noticeEvents.clear();
        }
    }
}
