package com.tass.productservice.domain;

import com.tass.productservice.database.entities.Category;
import com.tass.productservice.database.entities.CategoryRelationship;
import com.tass.productservice.database.repository.CategoryRepository;
import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.ERROR;
import com.tass.productservice.model.dto.CategoryInfo;
import com.tass.productservice.redis.dto.CategoryLinkedInfo;
import com.tass.productservice.redis.repository.CategoryLinkedRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Log4j2
public class CategoryDomain {
    @Autowired
    @Qualifier("redisCategoryLinkedRepository")
    private CategoryLinkedRepository redisCategoryLinkedRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryLinkedRepository categoryLinkedRepository;


    // update cache
    public void updateToRedis(CategoryRelationship.PK pk) throws ApiException{
        // update info

        long parentId = pk.getParentId();
        long childId = pk.getChildrenId();

        Category parent = getCategoryById(parentId);
        Category child = getCategoryById(childId);

        CategoryInfo parentInfo = new CategoryInfo(parent);
        CategoryInfo childInfo = new CategoryInfo(child);

        // update list linked of parent

        Optional<CategoryLinkedInfo> parentLinkInfoOpt = redisCategoryLinkedRepository.findById(parentId);
        CategoryLinkedInfo parentLinkedInfo = null;
        if (parentLinkInfoOpt.isEmpty()){
            parentLinkedInfo = new CategoryLinkedInfo();
            parentLinkedInfo.setId(parentId);
            parentLinkedInfo.setExpiredTime(300);
        }else {
            parentLinkedInfo = parentLinkInfoOpt.get();
        }

        List<CategoryInfo> listChild = parentLinkedInfo.getChild();

        if (listChild == null)
            listChild = new ArrayList<>();

        listChild.add(childInfo);

        parentLinkedInfo.setChild(listChild);
        redisCategoryLinkedRepository.save(parentLinkedInfo);


        // update list parentd of childId
        Optional<CategoryLinkedInfo> childLinkInfoOpt = redisCategoryLinkedRepository.findById(childId);
        CategoryLinkedInfo childLinkedInfo = null;
        if (childLinkInfoOpt.isEmpty()){
            childLinkedInfo = new CategoryLinkedInfo();
            childLinkedInfo.setId(childId);
            childLinkedInfo.setExpiredTime(300);
        }else {
            childLinkedInfo = childLinkInfoOpt.get();
        }



        List<CategoryInfo> listParent = childLinkedInfo.getParent();

        if (listParent == null)
            listParent = new ArrayList<>();

        listParent.add(parentInfo);

        childLinkedInfo.setParent(listParent);
        redisCategoryLinkedRepository.save(childLinkedInfo);

    }

    public void setLinkedInfoCategory(CategoryInfo category,  Category categoryEntity) throws ApiException{
        Optional<CategoryLinkedInfo> categoryLinkedInfoOpt = redisCategoryLinkedRepository.findById(category.getId())  ;

        if (categoryLinkedInfoOpt.isPresent()){

            CategoryLinkedInfo categoryLinkedInfo = categoryLinkedInfoOpt.get();

            category.setParents(categoryLinkedInfo.getParent());
            category.setChildren(categoryLinkedInfo.getChild());

            return;
        }

        // update cache

        List<Category> parents = categoryEntity.getParent();

        List<Category> child = categoryEntity.getChild();

        List<CategoryInfo> parentLinkedInfo = new ArrayList<>();

        List<CategoryInfo> childLinkedInfo = new ArrayList<>();


        if (!CollectionUtils.isEmpty(parents)){
            for (Category entity : parents){

                parentLinkedInfo.add(new CategoryInfo(entity));
            }
        }

        if (!CollectionUtils.isEmpty(child)){
            for (Category entity : child){

                childLinkedInfo.add(new CategoryInfo(entity));
            }
        }

        CategoryLinkedInfo categoryLinkedInfo = new CategoryLinkedInfo();
        categoryLinkedInfo.setId(category.getId());
        categoryLinkedInfo.setExpiredTime(300);
        categoryLinkedInfo.setParent(parentLinkedInfo);
        categoryLinkedInfo.setChild(childLinkedInfo);

        redisCategoryLinkedRepository.save(categoryLinkedInfo);


        category.setChildren(childLinkedInfo);
        category.setParents(parentLinkedInfo);
    }




    public Category getCategoryById(long id) throws ApiException{

        Optional<Category> categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isEmpty()){
            log.debug("not found category with id {}" , id);
            throw new ApiException(ERROR.INVALID_PARAM);
        }

        return categoryOpt.get();
    }

}
