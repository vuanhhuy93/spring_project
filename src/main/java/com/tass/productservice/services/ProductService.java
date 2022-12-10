package com.tass.productservice.services;

import com.tass.productservice.database.entities.Gln;
import com.tass.productservice.database.entities.Product;
import com.tass.productservice.database.repository.GlnRepository;
import com.tass.productservice.database.repository.ProductRepository;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.ERROR;
import com.tass.productservice.model.dto.GlnDTO;
import com.tass.productservice.model.dto.ProductDTO;
import com.tass.productservice.model.request.ProductRequest;
import com.tass.productservice.model.response.SearchProductResponse;
import com.tass.productservice.utils.JsonHelper;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    GlnRepository glnRepository;


    public BaseResponse createdProduct(ProductRequest request){

        return new BaseResponse();
    }
    public SearchProductResponse getById(long id) throws IOException {

        SearchProductResponse response = new SearchProductResponse();

        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()){
            response.setCode(ERROR.INVALID_PARAM.code);

            return response;
        }
        Product product = productOptional.get();

        ProductDTO dto = new ProductDTO(product);

        Gln gln = product.getGln();
        if (gln != null){
            GlnDTO glnDTO = new GlnDTO(gln);
            dto.setGln(glnDTO);
        }
        response.setData(dto);
        return response;
    }
}
