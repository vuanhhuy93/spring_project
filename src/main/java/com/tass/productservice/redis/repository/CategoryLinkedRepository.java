package com.tass.productservice.redis.repository;

import com.tass.productservice.redis.dto.CategoryLinkedInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("redisCategoryLinkedRepository")
public interface CategoryLinkedRepository extends CrudRepository<CategoryLinkedInfo , Long> {
}
