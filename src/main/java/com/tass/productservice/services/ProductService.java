package com.tass.productservice.services;

import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.ProductRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public BaseResponse createdProduct(ProductRequest request){

        return new BaseResponse();
    }
}
