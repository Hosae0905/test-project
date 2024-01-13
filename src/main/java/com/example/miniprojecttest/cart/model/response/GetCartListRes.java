package com.example.miniprojecttest.cart.model.response;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetCartListRes {
    @NotNull
    private Long idx;
    @NotNull
    private Long productIdx;
    @NotNull
    private String productName;
    @NotNull
    private Integer price;
    @NotNull
    private String image;
}
