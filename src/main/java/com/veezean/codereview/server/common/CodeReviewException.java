package com.veezean.codereview.server.common;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2021/6/7
 */
public class CodeReviewException extends RuntimeException {

    public CodeReviewException(String message) {
        super(message);
    }

    public CodeReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeReviewException(Throwable cause) {
        super(cause);
    }
}
