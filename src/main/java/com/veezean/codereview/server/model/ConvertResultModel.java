package com.veezean.codereview.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/6/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertResultModel<T> {
    private T targetResult;
    private String targetStringValue;
}
