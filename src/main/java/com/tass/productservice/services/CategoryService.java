package com.tass.productservice.services;

import com.tass.productservice.database.entities.Category;
import com.tass.productservice.database.entities.CategoryRelationship;
import com.tass.productservice.database.repository.CategoryRelationshipRepository;
import com.tass.productservice.database.repository.CategoryRepository;
import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.ERROR;
import com.tass.productservice.model.request.CategoryRequest;
import com.tass.productservice.model.response.SearchCategoryResponse;
import com.tass.productservice.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Log4j2
public class CategoryService {

    //    private Logger
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryRelationshipRepository categoryRelationshipRepository;


    // search


    public SearchCategoryResponse search(Integer isRoot , String name, Integer page, Integer pageSize){
        if (page == null || page < 1){
            page = 1;
        }
        if (pageSize == null || pageSize < 1){
            pageSize = 10;
        }

        SearchCategoryResponse.Data data = new SearchCategoryResponse.Data();
        data.setCurrentPage(page);
        data.setPageSize(pageSize);

        categoryRepository.searchCategory(isRoot , name, page, pageSize, data);

        SearchCategoryResponse response = new SearchCategoryResponse();
        response.setData(data);
        return response;
    }

    @Transactional
    public BaseResponse deleteCategory(Long id) throws ApiException {
        //step 1 : del category
        categoryRepository.deleteById(id);

        // step 2 : del children
        this.deleteCategoryImpl(id);
        return new BaseResponse();
    }


    @Transactional
    public BaseResponse editCategory(Long id, CategoryRequest request) throws ApiException {
        //step 1 : validate
        log.info("edit category with id : {} , json body {} ", id, request);
        validateRequestCreateException(request);

        // step 2 find category with id on database
        Optional<Category> categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isEmpty()) {
            log.debug("not found category with id {} on database", id);
            throw new ApiException(ERROR.INVALID_PARAM, "category not found");
        }

        Category category = categoryOpt.get();
        // step 3 : set value
        // step 3.1 value type

        if (request.getIsRoot() != null &&
            category.getIsRoot() != request.getIsRoot()) {

            log.debug("request change category type from {}  to type {}", category.getIsRoot(),
                request.getIsRoot());
            throw new ApiException(ERROR.INVALID_PARAM);
        }

        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        category.setName(request.getName());


        if (!category.checkIsRoot() && request.getParentId() != null) {

            // 3.2 validate relationship

            CategoryRelationship.PK pk =
                new CategoryRelationship.PK(request.getParentId(), category.getId());

            Optional<CategoryRelationship> categoryRelationshipOptional =
                categoryRelationshipRepository.findById(pk);

            if (categoryRelationshipOptional.isEmpty()){

                CategoryRelationship categoryRelationship = new CategoryRelationship();
                categoryRelationship.setPk(pk);

                categoryRelationshipRepository.save(categoryRelationship);
            }
        }


        log.info("edit category with id {} success", id);
        return new BaseResponse();
    }

    private void deleteCategoryImpl(long id) throws ApiException {
        List<CategoryRelationship> listChildren =
            categoryRelationshipRepository.findAllChildrenByParentId(id);

        if (CollectionUtils.isEmpty(listChildren)) {
            return;
        }

        List<CategoryRelationship> deleteRelationship = new ArrayList<>();
        for (CategoryRelationship cr : listChildren) {

            long countParent =
                categoryRelationshipRepository.countParent(cr.getPk().getChildrenId());

            if (countParent == 1) {
                deleteRelationship.add(cr);
                this.deleteCategoryImpl(cr.getPk().getChildrenId());
            }
        }

        if (!CollectionUtils.isEmpty(deleteRelationship)) {
            categoryRelationshipRepository.deleteAll(deleteRelationship);
        }
    }

    @Transactional
    public BaseResponse createCategory(CategoryRequest request) throws ApiException {

        // step  1 : validate request
        validateRequestCreateException(request);

        if (!request.checkIsRoot()) {
            // validate parent co ton tai khong

            Optional<Category> checkParentOpt = categoryRepository.findById(request.getParentId());

            if (checkParentOpt.isEmpty()) {
                throw new ApiException(ERROR.INVALID_PARAM, "parent is invalid");
            }

        }

        Category category = new Category();
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        category.setName(request.getName());
        category.setIsRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF);

        categoryRepository.save(category);

        if (!request.checkIsRoot()) {
            // tao quan he
            CategoryRelationship categoryRelationship = new CategoryRelationship();
            CategoryRelationship.PK pk =
                new CategoryRelationship.PK(request.getParentId(), category.getId());
            categoryRelationship.setPk(pk);
            categoryRelationshipRepository.save(categoryRelationship);
        }

        return new BaseResponse();
    }

    private void validateRequestCreateException(CategoryRequest request) throws ApiException {

        if (StringUtils.isBlank(request.getIcon())) {
            log.debug("icon is blank");
            throw new ApiException(ERROR.INVALID_PARAM, "icon is blank");
        }

        if (StringUtils.isBlank(request.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "name is banl");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApiException(ERROR.INVALID_PARAM, "description is Blank");
        }

        if (request.checkIsRoot() && request.getParentId() != null) {
            throw new ApiException(ERROR.INVALID_PARAM, "level is invalid");
        }

        if (!request.checkIsRoot() && request.getParentId() == null) {
            throw new ApiException(ERROR.INVALID_PARAM, "parent is blank");
        }
    }
}
