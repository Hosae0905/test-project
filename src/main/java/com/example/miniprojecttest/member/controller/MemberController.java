package com.example.miniprojecttest.member.controller;

import com.example.miniprojecttest.member.model.GetEmailConfirmReq;
import com.example.miniprojecttest.member.model.MemberLoginReq;
import com.example.miniprojecttest.member.model.MemberLoginRes;
import com.example.miniprojecttest.member.model.MemberSignupReq;
import com.example.miniprojecttest.member.service.EmailVerifyService;
import com.example.miniprojecttest.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailVerifyService emailVerifyService;

    @RequestMapping(method = RequestMethod.POST, value = "/consumer/signup")
    public ResponseEntity consumerSignup(@RequestBody MemberSignupReq memberSignupReq){
        memberService.consumerSignup(memberSignupReq);
        memberService.sendEmail(memberSignupReq);
        return ResponseEntity.ok().body("consumer 회원가입 성공");
    }
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(@RequestBody MemberLoginReq memberLoginReq){
        MemberLoginRes memberLoginRes = memberService.login(memberLoginReq);
        MemberLoginRes response = MemberLoginRes.builder()
                .token(memberLoginRes.getToken())
                .build();

        return ResponseEntity.ok().body(response);
    }
    @RequestMapping(method = RequestMethod.GET,value = "confirm")
    public RedirectView confirm(GetEmailConfirmReq getEmailConfirmReq){
        if(emailVerifyService.verify(getEmailConfirmReq.getEmail(), getEmailConfirmReq.getToken())) {
            memberService.update(getEmailConfirmReq.getEmail());
            return new RedirectView("http://localhost:3000/emailconfirm/" + getEmailConfirmReq.getJwt());
        }
        return new RedirectView("http://localhost:3000/emailCertError");

    }
}
