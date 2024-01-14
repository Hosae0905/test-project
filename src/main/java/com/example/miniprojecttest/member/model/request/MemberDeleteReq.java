package com.example.miniprojecttest.member.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDeleteReq {
    private String authority;
    private String email;
}
