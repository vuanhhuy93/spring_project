package com.tass.productservice.database.repository.impl;

import com.tass.productservice.database.repository.CategoryExtentRepository;
import com.tass.productservice.model.dto.CategoryInfo;
import com.tass.productservice.model.response.SearchCategoryResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

public class CategoryExtentRepositoryImpl implements CategoryExtentRepository {
    @PersistenceContext
    Session session;


    public void searchCategory(Integer isRoot, String name, Integer page, Integer pageSize,
                               SearchCategoryResponse.Data data) {

        StringBuilder baseSql = new StringBuilder();

        baseSql.append(" FROM com.tass.productservice.database.entities.Category c WHERE 1 = 1 ");

        if (isRoot != null) {
            baseSql.append(" AND c.isRoot = ").append(isRoot);
        }

        if (StringUtils.isNotBlank(name)) {
            baseSql.append(" AND c.name LIKE  '%").append(name).append("%'");
        }

        // select total item

        String sqlCount = "SELECT count(c) " + baseSql;

        Query query = session.createQuery(sqlCount);

        Object totalItemValue = query.getSingleResult();

        if (totalItemValue instanceof BigInteger) {
            BigInteger totalItem = (BigInteger) totalItemValue;
            data.setTotalItem(totalItem.longValue());
        } else if (totalItemValue instanceof Number) {
            data.setTotalItem((Long) totalItemValue);
        }

        // select item info

        if (data.getTotalItem() > 0) {

            String querySql =
                "SELECT new com.tass.productservice.model.dto.CategoryInfo(c.id, c.name, c.icon, c.description, c.isRoot) " +
                    baseSql;
            Query queryItem = session.createQuery(querySql, CategoryInfo.class);


            page--;
            int fistResult = page * pageSize;
            query.setMaxResults(pageSize).setFirstResult(fistResult);

            List<CategoryInfo> categoryList = queryItem.getResultList();

            data.setItems(categoryList);

        } else {
            data.setItems(new ArrayList<>());
        }
    }
}
