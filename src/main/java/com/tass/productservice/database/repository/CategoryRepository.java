package com.tass.productservice.database.repository;

import com.tass.productservice.database.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> , CategoryExtentRepository{

}
