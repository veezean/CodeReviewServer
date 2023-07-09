package com.veezean.codereview.server.service;

import cn.hutool.core.util.StrUtil;
import com.veezean.codereview.server.model.ValuePair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/6/6
 */
@Component
@Slf4j
public class CommentFieldShowContentProducer {
    @Autowired
    private ColumnDefineService columnDefineService;
    @Autowired
    private DictService dictService;

    /**
     * 将实际值转换为需要在界面上展示的值
     *
     * @param valuePair
     * @return
     */
    public String getColumnShowContent(ValuePair valuePair) {
        if (valuePair == null) {
            return "";
        }
        String showContent = valuePair.getShowName();
        if (StringUtils.isEmpty(showContent)) {
            showContent = valuePair.getValue();
        }
        return StrUtil.maxLength(showContent, 300);

//        ColumnDefineEntity columnDefineEntity = columnDefineService.queryColumnByCode(key);
//        if (columnDefineEntity != null
//                && InputTypeDefine.COMBO_BOX.getValue().equals(columnDefineEntity.getInputType())
//                && StringUtils.isNotEmpty(columnDefineEntity.getDictCollectionCode())
//        ) {
//            showContent =
//                    dictService.queryShowNameByCollectionCodeAndItemValue(columnDefineEntity.getDictCollectionCode(),
//                            valuePair.getValue());
//        }
//        return StrUtil.maxLength(showContent, 300);
    }
}
