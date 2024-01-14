package com.example.miniprojecttest.product.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder
@Data
public class GetProductListRes {
    Boolean isSuccess;
    Integer code;
    String message;
    List<GetProductReadRes> result;
    Boolean success;
}
