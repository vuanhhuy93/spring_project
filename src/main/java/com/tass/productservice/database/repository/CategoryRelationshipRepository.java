package com.tass.productservice.database.repository;

import com.tass.productservice.database.entities.CategoryRelationship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRelationshipRepository extends JpaRepository<CategoryRelationship, CategoryRelationship.PK> {
    @Query(value =  "select * from category_relationship cr WHERE cr.id = ?1" , nativeQuery = true)
    List<CategoryRelationship> findAllChildrenByParentId(long parentId);

    @Query(value =  "select count(*) from category_relationship cr WHERE cr.link_id = ?1" , nativeQuery = true)
    long countParent(long id);
}
