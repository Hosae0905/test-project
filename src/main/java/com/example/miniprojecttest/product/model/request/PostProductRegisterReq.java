package com.example.miniprojecttest.product.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PostProductRegisterReq {
    private String productName;
    private Integer price;
    private String productInfo;
    
    // TODO: 인원수, 마감일
}
