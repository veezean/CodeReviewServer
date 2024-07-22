package com.veezean.codereview.server.entity;

import com.veezean.codereview.server.common.SystemCommentFieldKey;
import com.veezean.codereview.server.model.ValuePair;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * <类功能简要描述>
 *
 * @author Veezean
 * @since 2023/6/25
 */
@Data
@Document(collection = "review_comment")
public class ReviewCommentEntity implements Serializable {
    private static final long serialVersionUID = -3143939214516758331L;
    @Id
    private String id;
    private long dataVersion;
    private Map<String, ValuePair> values;
    /**
     * 删除状态，1：已删除，0：未删除
     * 服务端标记，客户端无需感知，客户端的数据一定是未删除的
     */
    private int status;

    /**
     * 最近一次的操作类型(用于通知消息推送使用)
     */
    private int latestOperateType;

    public void increaseDataVersion() {
        this.dataVersion++;
    }

    public Optional<ValuePair> findByKey(SystemCommentFieldKey key) {
        return Optional.ofNullable(values).map(map -> values.get(key.getCode()));
    }
}
