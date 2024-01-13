package com.example.miniprojecttest.member.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberSignupReq {
    private String consumerID;
    private String consumerPW;
    private String consumerName;
    private String consumerAddr;
    private String consunmerPhoneNum;


}
