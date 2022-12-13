package com.tass.productservice.database.repository;

import com.tass.productservice.database.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
