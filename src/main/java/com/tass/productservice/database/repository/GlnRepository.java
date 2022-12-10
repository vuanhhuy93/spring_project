package com.tass.productservice.database.repository;

import com.tass.productservice.database.entities.Gln;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlnRepository extends JpaRepository<Gln, Long> {
}
