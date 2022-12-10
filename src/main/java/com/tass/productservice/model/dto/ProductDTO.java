package com.tass.productservice.model.dto;

import com.tass.productservice.database.entities.Product;
import lombok.Data;

@Data
public class ProductDTO {
    private long id;
    private String code;
    private String description;

    private GlnDTO gln;

    public ProductDTO(){

    }

    public ProductDTO(Product entity){
        this.id = entity.getId();
        this.code = entity.getCode();
        this.description = entity.getDescription();
    }
}
