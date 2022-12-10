package com.tass.productservice.model.response;

import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.dto.ProductDTO;
import lombok.Data;

@Data
public class SearchProductResponse extends BaseResponse {
    private ProductDTO data;
}
