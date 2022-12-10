package com.tass.productservice.database.repository;

import com.tass.productservice.database.entities.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from product p where p.code = ?1" , nativeQuery = true)
    List<Product> findAllByCode(String code);
}
