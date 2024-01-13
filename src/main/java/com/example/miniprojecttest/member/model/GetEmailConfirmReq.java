package com.example.miniprojecttest.member.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetEmailConfirmReq {
    private String email;
    private String token;
    private String jwt;
}
