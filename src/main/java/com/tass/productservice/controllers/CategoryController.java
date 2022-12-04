package com.tass.productservice.controllers;

import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.CategoryRequest;

import com.tass.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController{

    @Autowired
    CategoryService categoryService;

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
