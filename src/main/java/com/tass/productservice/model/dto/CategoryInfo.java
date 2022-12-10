package com.tass.productservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tass.productservice.database.entities.Category;
import java.util.List;
import lombok.Data;

@Data
public class CategoryInfo {
    private Long id;
    private String name;
    private String icon;
    private String description;
    @JsonProperty("is_root")
    private Integer isRoot;
    @JsonProperty("parents")
    private List<CategoryInfo> parents;

    @JsonProperty("childs")
    private List<CategoryInfo> children;

    public CategoryInfo(){

    }

    public CategoryInfo(Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.icon = entity.getIcon();
        this.description = entity.getDescription();
        this.isRoot = entity.getIsRoot();
    }

    public CategoryInfo(Long id, String name, String icon, String description,  Integer isRoot){
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon =  icon;
        this.isRoot = isRoot;
    }
}
