package com.tass.productservice.database.entities;

import com.tass.productservice.utils.Constant;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "category_relationship" ,joinColumns = @JoinColumn(name = "id")
        ,inverseJoinColumns = @JoinColumn(name = "link_id") )
    private List<Category> child;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "category_relationship" ,joinColumns = @JoinColumn(name = "link_id")
        ,inverseJoinColumns = @JoinColumn(name = "id") )
    private List<Category> parent;


    public boolean checkIsRoot(){
        return isRoot == Constant.ONOFF.ON;
    }

}
