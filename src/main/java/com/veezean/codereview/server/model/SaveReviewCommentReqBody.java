package com.veezean.codereview.server.model;

import com.veezean.codereview.server.common.CodeReviewException;
import com.veezean.codereview.server.common.SystemCommentFieldKey;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/3/25
 */
@Data
public class SaveReviewCommentReqBody {
    private List<CommentFieldVO> fieldModelList;
    private long dataVersion;
    private String codeType = "java";

    public String findIdentifier() {
        return getValueByKey(SystemCommentFieldKey.IDENTIFIER);
    }

    public String getValueByKey(SystemCommentFieldKey key) {
        if (fieldModelList != null) {
            CommentFieldVO fieldVO = fieldModelList.stream()
                    .filter(commentFieldVO -> StringUtils.equals(key.getCode(),
                            commentFieldVO.getCode()))
                    .findFirst().orElseThrow(() -> new CodeReviewException(key.getCode() + "字段缺失"));
            return Optional.ofNullable(fieldVO)
                    .map(CommentFieldVO::getValuePair)
                    .map(ValuePair::getValue)
                    .orElseThrow(() -> new CodeReviewException(key.getCode() + "未获取到"));
        }
        throw new CodeReviewException(key.getCode() + "字段缺失");
    }
}
