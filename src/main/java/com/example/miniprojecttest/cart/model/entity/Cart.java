package com.example.miniprojecttest.cart.model.entity;

import com.example.miniprojecttest.member.model.entity.Member;
import com.example.miniprojecttest.product.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품이랑 연관관계 맺기
    @ManyToOne
    @JoinColumn(name = "Product_idx")
    private Product product;

    // 구매자랑 연관관계 맺기
    @ManyToOne
    @JoinColumn(name = "Member_idx")
    private Member member;
}
