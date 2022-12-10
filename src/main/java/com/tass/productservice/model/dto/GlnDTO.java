package com.tass.productservice.model.dto;

import com.tass.productservice.database.entities.Gln;
import java.util.List;
import lombok.Data;

@Data
public class GlnDTO {
    private long id;
    private String name;
    private String description;
    private List<ProductDTO> products;

    public GlnDTO(){

    }

    public GlnDTO(Gln entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
