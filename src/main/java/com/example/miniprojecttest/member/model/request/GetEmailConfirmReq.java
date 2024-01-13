package com.example.miniprojecttest.member.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class GetEmailConfirmReq {
    private String email;
    private String token;
    private String jwt;
    private String authority;
}
