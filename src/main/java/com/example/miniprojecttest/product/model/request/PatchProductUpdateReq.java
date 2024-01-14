package com.example.miniprojecttest.product.model.request;

import com.example.miniprojecttest.member.model.entity.Seller;
import com.example.miniprojecttest.product.model.entity.ProductImage;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class PatchProductUpdateReq {
    private Long id;
    private String productName;
    private Integer price;
    private Integer salePrice;
    private String productInfo;
    private List<ProductImage> productImages;
}
