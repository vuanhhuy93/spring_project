package com.tass.productservice.model.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String barcode;
    private String name;
    private String image;
    private String description;
    private String content;

}
