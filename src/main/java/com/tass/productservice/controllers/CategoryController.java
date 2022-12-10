package com.tass.productservice.controllers;

import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.CategoryRequest;

import com.tass.productservice.model.response.CategoryDetailResponse;
import com.tass.productservice.model.response.SearchCategoryResponse;
import com.tass.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController{

    @Autowired
    CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDetailResponse findById(@PathVariable Long id){
        return categoryService.getById(id);
    }

    @GetMapping
    public SearchCategoryResponse search(@RequestParam(name = "is_root" , required = false) Integer isRoot , @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer page , @RequestParam(name = "page_size" , required = false) Integer pageSize){
        return categoryService.search(isRoot, name, page, pageSize);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CategoryRequest request)throws
        ApiException {
        return createdResponse(categoryService.createCategory(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id)throws
        ApiException {
        return createdResponse(categoryService.deleteCategory(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> edit(@PathVariable Long id , @RequestBody CategoryRequest request)throws
        ApiException {
        return createdResponse(categoryService.editCategory(id , request));
    }
}
