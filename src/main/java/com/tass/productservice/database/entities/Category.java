package com.tass.productservice.database.entities;

import com.tass.productservice.utils.Constant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String icon;

    private int isRoot;

    public boolean checkIsRoot(){
        return isRoot == Constant.ONOFF.ON;
    }

}
