package com.example.miniprojecttest.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginRes {
    String token;
}
