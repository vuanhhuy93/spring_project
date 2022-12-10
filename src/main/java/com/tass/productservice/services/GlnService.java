package com.tass.productservice.services;

import com.tass.productservice.database.entities.Gln;
import com.tass.productservice.database.entities.Product;
import com.tass.productservice.database.repository.GlnRepository;
import com.tass.productservice.model.ERROR;
import com.tass.productservice.model.dto.GlnDTO;
import com.tass.productservice.model.dto.ProductDTO;
import com.tass.productservice.model.response.SearchGlnResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GlnService {
    @Autowired
    private GlnRepository glnRepository;


    public SearchGlnResponse findById(long id){
        SearchGlnResponse response = new SearchGlnResponse();

        Optional<Gln> glnOptional = glnRepository.findById(id);

        if (glnOptional.isEmpty()){

            response.setCode(ERROR.INVALID_PARAM.code);
            return response;
        }

        Gln gln = glnOptional.get();

        GlnDTO dto = new GlnDTO(gln);

        if (!CollectionUtils.isEmpty(gln.getProducts())){
            List<ProductDTO> products = new ArrayList<>();

            for (Product product : gln.getProducts()){
                products.add(new ProductDTO(product));
            }

            dto.setProducts(products);
        }

        response.setData(dto);
        return response;

    }
}
