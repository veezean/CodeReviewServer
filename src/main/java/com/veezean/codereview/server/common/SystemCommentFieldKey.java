package com.veezean.codereview.server.common;

import com.veezean.codereview.server.common.convertor.CommonDateFieldValueConvertor;
import com.veezean.codereview.server.common.convertor.CommonLongFieldValueConvertor;
import com.veezean.codereview.server.common.convertor.CommonStringFieldValueConvertor;
import com.veezean.codereview.server.common.convertor.FieldValueConvertor;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统预置评审字段code值
 *
 * @author Veezean
 * @since 2023/5/30
 */
@AllArgsConstructor
@Getter
public enum SystemCommentFieldKey {

    IDENTIFIER("identifier",  new CommonStringFieldValueConvertor()),
    PROJECT_ID("projectId", new CommonLongFieldValueConvertor()),
    CONFIRM_RESULT("confirmResult",  new CommonStringFieldValueConvertor()),

    REVIEWER("reviewer",  new CommonStringFieldValueConvertor()),
    ASSIGN_CONFIRMER("assignConfirmer", new CommonStringFieldValueConvertor()),
    REAL_CONFIRMER("realConfirmer", new CommonStringFieldValueConvertor()),
    REVIEW_DATE("reviewDate", new CommonDateFieldValueConvertor()),
    CONFIRM_DATE("confirmDate", new CommonDateFieldValueConvertor());

    private String code;
    private FieldValueConvertor valueConvertor;
}
