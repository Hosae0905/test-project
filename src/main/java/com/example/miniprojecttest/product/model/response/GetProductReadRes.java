package com.example.miniprojecttest.product.model.response;

import com.example.miniprojecttest.member.model.entity.Seller;
import com.example.miniprojecttest.product.model.entity.ProductImage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetProductReadRes {
    private Long idx;
    private String productName;
    private Integer price;
    private Integer salePrice;
    private String productInfo;
    private List<ProductImage> productImages;
    private Seller sellerIdx ;

}
