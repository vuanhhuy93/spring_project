package com.tass.productservice.database.repository;

import com.tass.productservice.database.entities.UserRole;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRole , Long> {

    @Query(value = "select ur.role from UserRole ur Where ur.userId = ?1 and ur.status = 1")
    Set<String> getUserRoleActiveByUserId(long userId);
}
