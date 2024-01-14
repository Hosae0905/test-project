package com.example.miniprojecttest.product.model.entity;

import com.example.miniprojecttest.cart.model.entity.Cart;
import com.example.miniprojecttest.category.model.entity.Category;
import com.example.miniprojecttest.member.model.entity.Seller;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
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

    // 판매자 테이블과 연관 관계 매핑 설정 완료
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Customer_ID")
    private Seller sellerIdx ;

    // 판매자 ID


    // 카테고리 ID
    @ManyToOne
    @JoinColumn(name = "Category_idx")
    private Category category;

    // 장바구니
    @OneToMany(mappedBy = "product")
    private List<Cart> carts;
}
