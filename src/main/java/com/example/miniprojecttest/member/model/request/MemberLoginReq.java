package com.example.miniprojecttest.member.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class MemberLoginReq {
    @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String username;
    private String password;
}
