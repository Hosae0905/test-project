package com.example.miniprojecttest.member.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
@Data
@Builder
public class SendEmailReq {     //정규식 필요 x)
    private String email;
    private String authority;

}
