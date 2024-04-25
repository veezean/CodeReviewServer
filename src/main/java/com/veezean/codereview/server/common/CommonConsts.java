package com.veezean.codereview.server.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/22
 */
public class CommonConsts {

    public static final long ONE_DAY_MILLIS = 1000L * 60 * 60 * 24;

    public static final String UNCONFIRMED = "unconfirmed";

    public static final String ALL_MENUS = "reviewcomments,mytodo,mycommitted,myconfirmed,all,servMgt,commentFields," +
            "enums,systemConfig,users,roles,depts,projs,reports,dashboard";

    public static final Map<String, String> fileSuffixAndCodeTypeMaps = new HashMap<>();
    static {
        fileSuffixAndCodeTypeMaps.put("js", "javascript");
        fileSuffixAndCodeTypeMaps.put("java", "java");
        fileSuffixAndCodeTypeMaps.put("yaml", "yaml");
        fileSuffixAndCodeTypeMaps.put("json", "json");
        fileSuffixAndCodeTypeMaps.put("sql", "sql");
        fileSuffixAndCodeTypeMaps.put("xml", "xml");
        fileSuffixAndCodeTypeMaps.put("html", "html");
    }

}
