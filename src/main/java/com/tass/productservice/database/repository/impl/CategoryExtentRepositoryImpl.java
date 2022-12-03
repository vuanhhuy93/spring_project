package com.tass.productservice.database.repository.impl;

import com.tass.productservice.database.repository.CategoryExtentRepository;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryExtentRepositoryImpl implements CategoryExtentRepository {
    @PersistenceContext
    Session session;
}
