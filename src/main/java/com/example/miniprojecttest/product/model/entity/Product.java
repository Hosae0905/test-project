package com.example.miniprojecttest.product.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String productName;
    private Integer price;
    private String productInfo;

//    private Integer peopleCount;
//    private Date startAt;
//    private Date closeAt;

    // TODO: 연관관계 설정 후 외래키 지정
    @OneToMany(mappedBy = "product")
    private List<ProductImage> images;

    // 판매자 ID
    // 카테고리 ID
}
