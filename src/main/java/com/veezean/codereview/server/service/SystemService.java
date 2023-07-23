package com.veezean.codereview.server.service;

import com.veezean.codereview.server.model.NoticeBody;
import com.veezean.codereview.server.model.RecordColumns;
import com.veezean.codereview.server.repository.ColumnDefineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/5
 */
@Service
@Slf4j
public class SystemService {

    private static final String SERVER_DYNAMIC_ENUM_PREFIX = "ServerDynamic_";

    @Autowired
    private ColumnDefineRepository columnDefineRepository;


    public List<NoticeBody> getSystemNotice() {
        Map<String, Object> params = new HashMap<>();
        params.put("code", "SystemNotice");

        // 使用数据字典中SystemNotice编码对应的枚举值作为系统通知配置
//        return Optional.ofNullable(eruptDao.queryEntityList(EruptDictItem.class, "eruptDict.code = :code", params))
//                .orElse(new ArrayList<>())
//                .stream()
//                .map(eruptDictItem -> {
//                    NoticeBody noticeBody = new NoticeBody();
//                    noticeBody.setMsg(eruptDictItem.getRemark());
//                    return noticeBody;
//                })
//                .collect(Collectors.toList());

        return null;
    }

    @Transactional
    public void initColumnDefines(RecordColumns recordColumns) {
        log.info("重置初始化评审字段配置信息：{}", recordColumns);
        columnDefineRepository.deleteAll();
        columnDefineRepository.saveAll(recordColumns.getColumns());
        log.info("评审字段配置信息重置完成");
    }
}
