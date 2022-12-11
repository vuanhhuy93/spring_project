package com.tass.productservice.redis.dto;

import com.tass.productservice.model.dto.CategoryInfo;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("category_link")
@Data
public class CategoryLinkedInfo {
    @Id
    private long id;

    private List<CategoryInfo> child;

    private List<CategoryInfo> parent;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long expiredTime;
}
