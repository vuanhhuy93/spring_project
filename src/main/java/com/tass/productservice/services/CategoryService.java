package com.tass.productservice.services;

import com.tass.productservice.database.entities.Category;
import com.tass.productservice.database.entities.CategoryRelationship;
import com.tass.productservice.database.repository.CategoryRelationshipRepository;
import com.tass.productservice.database.repository.CategoryRepository;
import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.ERROR;
import com.tass.productservice.model.request.CategoryRequest;
import com.tass.productservice.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class CategoryService {

//    private Logger
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryRelationshipRepository categoryRelationshipRepository;

    @Transactional
    public BaseResponse deleteCategory(Long id) throws ApiException{
        //step 1 : del category
        categoryRepository.deleteById(id);

        // step 2 : del children
        this.deleteCategoryImpl(id);
        return new BaseResponse();
    }

    private void deleteCategoryImpl(long id) throws ApiException{
        List<CategoryRelationship> listChildren = categoryRelationshipRepository.findAllChildrenByParentId(id);

        if (CollectionUtils.isEmpty(listChildren))
            return;

        List<CategoryRelationship> deleteRelationship = new ArrayList<>();
        for (CategoryRelationship cr :  listChildren){

            long countParent = categoryRelationshipRepository.countParent(cr.getPk().getChildrenId());

            if (countParent == 1){
                deleteRelationship.add(cr);
                this.deleteCategoryImpl(cr.getPk().getChildrenId());
            }
        }

        if (!CollectionUtils.isEmpty(deleteRelationship)){
            categoryRelationshipRepository.deleteAll(deleteRelationship);
        }
    }

    @Transactional
    public BaseResponse createCategory(CategoryRequest request) throws ApiException{

        // step  1 : validate request
        validateRequestCreateException(request);

        if (!request.checkIsRoot()){
            // validate parent co ton tai khong

            Optional<Category> checkParentOpt = categoryRepository.findById(request.getParentId());

            if (checkParentOpt.isEmpty()){
                throw new ApiException(ERROR.INVALID_PARAM , "parent is invalid");
            }

        }

        Category category = new Category();
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        category.setName(request.getName());
        category.setIsRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF);

        categoryRepository.save(category);

        if (!request.checkIsRoot()){
            // tao quan he

            CategoryRelationship categoryRelationship = new CategoryRelationship();
            CategoryRelationship.PK pk = new CategoryRelationship.PK(request.getParentId(), category.getId());
            categoryRelationship.setPk(pk);
            categoryRelationshipRepository.save(categoryRelationship);
        }

        return new BaseResponse();
    }

    private void validateRequestCreateException(CategoryRequest request) throws ApiException{

        if (StringUtils.isBlank(request.getIcon())){
            throw new ApiException(ERROR.INVALID_PARAM , "icon is blank");
        }

        if (StringUtils.isBlank(request.getName())){
            throw new ApiException(ERROR.INVALID_PARAM , "name is banl");
        }

        if (StringUtils.isBlank(request.getDescription())){
            throw new ApiException(ERROR.INVALID_PARAM , "description is Blank");
        }

        if (request.checkIsRoot() && request.getParentId() != null){
            throw new ApiException(ERROR.INVALID_PARAM , "level is invalid");
        }

        if (!request.checkIsRoot() && request.getParentId() == null){
            throw new ApiException(ERROR.INVALID_PARAM , "parent is blank");
        }
    }
}
