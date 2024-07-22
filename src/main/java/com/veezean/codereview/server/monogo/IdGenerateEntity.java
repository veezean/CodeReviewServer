package com.veezean.codereview.server.monogo;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * <类功能简要描述>
 *
 * @author Wang Weiren
 * @since 2024/7/21
 */
@Data
@Document(collection = "t_id_generate")
public class IdGenerateEntity implements Serializable {
    @Indexed(unique = true)
    private String key;
    private long seqId;
}
