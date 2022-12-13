package com.tass.productservice.database.repository;

import com.tass.productservice.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findFirstByUserName(String userName);
}
