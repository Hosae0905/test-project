package com.example.miniprojecttest.category.model.entity;


import com.example.miniprojecttest.product.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Category {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    private String region;
    private String type;

    @OneToMany(mappedBy = "category")
    private List<CategoryToProduct> categoryList;

}
