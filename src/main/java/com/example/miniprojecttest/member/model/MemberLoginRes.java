package com.example.miniprojecttest.member.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginRes {
    String token;
}
