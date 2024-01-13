package com.example.miniprojecttest.member.controller;

import com.example.miniprojecttest.member.model.entity.Member;
import com.example.miniprojecttest.member.model.entity.Seller;
import com.example.miniprojecttest.member.model.request.*;
import com.example.miniprojecttest.member.service.EmailVerifyService;
import com.example.miniprojecttest.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailVerifyService emailVerifyService;

    @RequestMapping(method = RequestMethod.POST, value = "/consumer/signup")
    public ResponseEntity consumerSignup(@RequestBody MemberSignupReq memberSignupReq){
        memberService.consumerSignup(memberSignupReq);

        return ResponseEntity.ok().body("consumer 회원가입 성공");
    }
    @RequestMapping(method = RequestMethod.POST, value = "/seller/signup")
    public ResponseEntity sellerSignup(@RequestBody SellerSignupReq sellerSignupReq){
        memberService.sellerSignup(sellerSignupReq);

        return ResponseEntity.ok().body("seller 회원가입 성공");
    }


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(@RequestBody MemberLoginReq memberLoginReq){
        Map response = memberService.login(memberLoginReq);

        return ResponseEntity.ok().body(response);
    }
    @RequestMapping(method = RequestMethod.GET,value = "confirm")
    public RedirectView confirm(GetEmailConfirmReq getEmailConfirmReq){

        return emailVerifyService.verify(getEmailConfirmReq);


        // TODO: 응답코드
    }
}
