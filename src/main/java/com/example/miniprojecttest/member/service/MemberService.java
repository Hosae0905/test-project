package com.example.miniprojecttest.member.service;

import com.example.miniprojecttest.member.model.entity.Member;
import com.example.miniprojecttest.member.model.entity.Seller;
import com.example.miniprojecttest.member.model.request.MemberLoginReq;
import com.example.miniprojecttest.member.model.request.MemberSignupReq;
import com.example.miniprojecttest.member.model.request.SellerSignupReq;
import com.example.miniprojecttest.member.model.request.SendEmailReq;
import com.example.miniprojecttest.member.repository.MemberRepository;
import com.example.miniprojecttest.member.repository.SellerRepository;
import com.example.miniprojecttest.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;
    private final EmailVerifyService emailVerifyService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    public Member consumerSignup(MemberSignupReq memberSignupReq) {
        Member member = Member.builder()
                .consumerID(memberSignupReq.getConsumerID())
                .consumerPW(passwordEncoder.encode(memberSignupReq.getConsumerPW()))
                .consumerName(memberSignupReq.getConsumerName())
                .consumerAddr(memberSignupReq.getConsumerAddr())
                .consumerPhoneNum(memberSignupReq.getConsumerPhoneNum())
                .authority("CONSUMER")
                .socialLogin(false)
                .status(false)
                .build();

        SendEmailReq sendEmailReq = SendEmailReq.builder()
                .email(member.getConsumerID())
                .authority(member.getAuthority())
                .build();

        sendEmail(sendEmailReq);
        memberRepository.save(member);
        return member;

    }
    public Seller sellerSignup(SellerSignupReq sellerSignupReq) {
        Seller seller = Seller.builder()
                .sellerID(sellerSignupReq.getSellerID())
                .sellerPW(sellerSignupReq.getSellerPW())
                .sellerName(sellerSignupReq.getSellerName())
                .sellerAddr(sellerSignupReq.getSellerAddr())
                .sellerPhoneNum(sellerSignupReq.getSellerPhoneNum())
                .sellerBusinessNumber(sellerSignupReq.getSellerBusinessNumber())
                .authority("SELLER")
                .status(false)
                .build();

        SendEmailReq sendEmailReq = SendEmailReq.builder()
                .email(seller.getSellerID())
                .authority(seller.getAuthority())
                .build();

        sendEmail(sendEmailReq);
        sellerRepository.save(seller);

        return seller;

    }

    public Map<String,String> login(MemberLoginReq memberLoginReq) {
        Optional<Member> member = memberRepository.findByConsumerID(memberLoginReq.getUsername());

        if (member.isPresent()) {
            if (passwordEncoder.matches(memberLoginReq.getPassword(), member.get().getPassword())) {

                Map<String, String> response = new HashMap<>();
                response.put("token", JwtUtils.generateAccessToken(member.get().getUsername(), secretKey, expiredTimeMs));
                return response;

            }else {
                return null;
            }
        }
        return null;
    }


    public void sendEmail(SendEmailReq sendEmailReq) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendEmailReq.getEmail());
        message.setSubject("로컬푸드 pampam 이메일 인증");
        String uuid = UUID.randomUUID().toString();
        message.setText("http://localhost:8080/member/confirm?email="
                + sendEmailReq.getEmail()
                + "&authority=" + sendEmailReq.getAuthority()
                + "&token=" + uuid
                + "&jwt=" + JwtUtils.generateAccessToken(sendEmailReq.getEmail(), secretKey, expiredTimeMs)
        );
        emailSender.send(message);

        emailVerifyService.create(sendEmailReq.getEmail(), uuid);
    }


    public Member getMemberByConsumerID(String consumerID) {
        Optional<Member> result = memberRepository.findByConsumerID(consumerID);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByConsumerID(username);
        Member member = null;
        if (result.isPresent())
            member = result.get();

        return member;
    }
}
