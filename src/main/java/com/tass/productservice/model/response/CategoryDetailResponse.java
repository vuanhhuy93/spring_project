package com.tass.productservice.model.response;

import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.dto.CategoryInfo;
import lombok.Data;

@Data
public class CategoryDetailResponse extends BaseResponse {
    private CategoryInfo data;
}
