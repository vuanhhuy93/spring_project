package com.tass.productservice.model.response;

import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.dto.GlnDTO;
import lombok.Data;

@Data
public class SearchGlnResponse extends BaseResponse {
    private GlnDTO data;
}