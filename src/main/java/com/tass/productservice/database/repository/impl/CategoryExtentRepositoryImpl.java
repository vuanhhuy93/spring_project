package com.tass.productservice.database.repository.impl;

import com.tass.productservice.database.repository.CategoryExtentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryExtentRepositoryImpl implements CategoryExtentRepository {
    @Autowired
    Session session;
}
