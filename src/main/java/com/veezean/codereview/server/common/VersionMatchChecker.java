package com.veezean.codereview.server.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务端与客户端版本匹配关系校验
 *
 * @author Veezean
 * @since 2024/4/27
 */
@Component
@Slf4j
public class VersionMatchChecker {

    private static final Map<String, VersionRange> versionMatchDefines = new HashMap<>();
    private static final Map<String, String> versionDescUrls = new HashMap<>();

    static {
        versionMatchDefines.put("4.1.1", VersionRange.of("4.0.3", ""));
        versionMatchDefines.put("4.1.2", VersionRange.of("4.0.3", ""));
        versionMatchDefines.put("4.1.3", VersionRange.of("4.0.3", ""));

        versionDescUrls.put("4.1.1", "https://mp.weixin.qq.com/s/yTR0iTDNGcpzQqvbS7DkjQ");
        versionDescUrls.put("4.1.2", "https://mp.weixin.qq.com/s/yTR0iTDNGcpzQqvbS7DkjQ");
        versionDescUrls.put("4.1.3", "https://mp.weixin.qq.com/s/yTR0iTDNGcpzQqvbS7DkjQ");
    }

    @Value("${application.server.version:}")
    private String currentServerVersion;

    /**
     * 当前服务端版本号
     *
     * @return
     */
    public String currentVersion() {
        return currentServerVersion;
    }

    public String getLatestDescUrl() {
        return versionDescUrls.entrySet().stream()
                .sorted((o1, o2) -> {
                    int o1Version = getIntVersionSafely(o1.getKey());
                    int o2Version = getIntVersionSafely(o2.getKey());
                    return o2Version - o1Version;
                })
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("");
    }

    /**
     * 检查客户端版本与服务端版本之间的匹配兼容情况
     *
     * @param clientVersion 客户端版本
     * @return
     */
    public VersionCheckResult versionCheck(String clientVersion) {
        int version = getIntVersionSafely(clientVersion);
        if (version != -1) {
            VersionRange versionRange = versionMatchDefines.get(currentServerVersion);
            if (versionRange != null) {
                int minVersion = getIntVersionSafely(versionRange.getMinClientVersion());
                if (minVersion != -1) {
                    if (version < minVersion) {
                        return VersionCheckResult.clientVersionTooLowerError(versionRange.getMinClientVersion());
                    }
                }
                int maxVersion = getIntVersionSafely(versionRange.getMaxClientVersion());
                if (maxVersion != -1) {
                    if (version > maxVersion) {
                        return VersionCheckResult.clientVersionTooHigherError(versionRange.getMaxClientVersion());
                    }
                }
            }
        }
        // 其余情况，均按照pass处理，降低杀伤力
        return VersionCheckResult.pass();
    }

    private static int getIntVersionSafely(String version) {
        if (StringUtils.isNotEmpty(version)) {
            version = version.replaceAll("\\.", "").trim();
            if (StringUtils.isNotEmpty(version)) {
                try {
                    return Integer.parseInt(version);
                } catch (Exception e) {
                    log.warn("failed to convert version to intVersion", e);
                }
            }
        }
        return -1;
    }

    @Data
    private static class VersionRange {
        /**
         * 配套的客户端最低版本号
         */
        private String minClientVersion;
        /**
         * 配套的客户端最高版本号，如果不填，则上不封顶
         */
        private String maxClientVersion;

        static VersionRange of(String minVersion, String maxVersion) {
            VersionRange define = new VersionRange();
            define.minClientVersion = minVersion;
            define.maxClientVersion = maxVersion;
            return define;
        }
    }

    /**
     * 服务端与客户端版本兼容性检查结果
     *
     * @author Veezean
     * @date 2024/4/27
     */
    @Data
    public static class VersionCheckResult {
        private boolean match;
        private String notice;

        public static VersionCheckResult pass() {
            VersionCheckResult result = new VersionCheckResult();
            result.setMatch(true);
            result.setNotice("校验通过，客户端与服务端版本兼容");
            return result;
        }

        /**
         * 插件版本过低，服务端不兼容
         *
         * @param minClientVersion 当前服务端支持的最低插件版本
         * @return
         */
        public static VersionCheckResult clientVersionTooLowerError(String minClientVersion) {
            VersionCheckResult result = new VersionCheckResult();
            result.setMatch(false);
            result.setNotice("插件客户端版本过低，与服务端不匹配，请将插件版本升级到" + minClientVersion + "+版本。");
            return result;
        }

        /**
         * 客户端版本过高，当前服务端版本不支持
         *
         * @param maxClientVersion 当前服务端支持的最高版本号
         * @return
         */
        public static VersionCheckResult clientVersionTooHigherError(String maxClientVersion) {
            VersionCheckResult result = new VersionCheckResult();
            result.setMatch(false);
            result.setNotice("插件客户端与服务端不匹配，当前服务端仅支持" + maxClientVersion + "及以下版本，请升级服务端版本。");
            return result;
        }
    }
}
