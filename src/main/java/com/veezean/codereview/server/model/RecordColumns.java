package com.veezean.codereview.server.model;

import com.veezean.codereview.server.entity.ColumnDefineEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Veezean, 公众号 @架构悟道
 * @since 2023/3/5
 */
@Data
public class RecordColumns {
    private List<ColumnDefineEntity> columns;
}
