package com.tass.productservice.controllers;

import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.ProductRequest;
import com.tass.productservice.model.response.SearchProductResponse;
import com.tass.productservice.services.ProductService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController{

    @Autowired
    ProductService productService;

    @PostMapping
    @Secured(value = {"CATEGORY_ALL"})
    public ResponseEntity<BaseResponse> create(@RequestBody ProductRequest request){
        return createdResponse(productService.createdProduct(request));
    }

    @GetMapping("/{id}")
    public SearchProductResponse getProductById(@PathVariable long id) throws IOException {
        return productService.getById(id);
    }
}
