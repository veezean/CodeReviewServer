package com.veezean.codereview.server.model;

import com.veezean.codereview.server.entity.ColumnDefineEntity;
import lombok.Data;

import java.util.List;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2023/3/5
 */
@Data
public class RecordColumns {
    private List<ColumnDefineEntity> columns;
}
