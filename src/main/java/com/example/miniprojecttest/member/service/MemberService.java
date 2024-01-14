package com.example.miniprojecttest.member.service;

import com.example.miniprojecttest.member.model.entity.Member;
import com.example.miniprojecttest.member.model.entity.Seller;
import com.example.miniprojecttest.member.model.request.*;
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
        Member member = memberRepository.save(Member.builder()
                .email(memberSignupReq.getEmail())
                .consumerPW(passwordEncoder.encode(memberSignupReq.getConsumerPW()))
                .consumerName(memberSignupReq.getConsumerName())
                .consumerAddr(memberSignupReq.getConsumerAddr())
                .consumerPhoneNum(memberSignupReq.getConsumerPhoneNum())
                .authority("CONSUMER")
                .socialLogin(false)
                .status(false)
                .build());

        String accessToken = JwtUtils.generateAccessToken(member, secretKey, expiredTimeMs);

        SendEmailReq sendEmailReq = SendEmailReq.builder()
                .email(member.getEmail())
                .authority(member.getAuthority())
                .accessToken(accessToken)
                .build();

        sendEmail(sendEmailReq);

        return member;

    }
    public Seller sellerSignup(SellerSignupReq sellerSignupReq) {
        Seller seller = sellerRepository.save(Seller.builder()
                .email(sellerSignupReq.getEmail())
                .sellerPW(passwordEncoder.encode(sellerSignupReq.getSellerPW()))
                .sellerName(sellerSignupReq.getSellerName())
                .sellerAddr(sellerSignupReq.getSellerAddr())
                .sellerPhoneNum(sellerSignupReq.getSellerPhoneNum())
                .sellerBusinessNumber(sellerSignupReq.getSellerBusinessNumber())
                .authority("SELLER")
                .status(false)
                .build());

        String accessToken = JwtUtils.generateAccessToken(seller, secretKey, expiredTimeMs);

        SendEmailReq sendEmailReq = SendEmailReq.builder()
                .email(seller.getEmail())
                .authority(seller.getAuthority())
                .accessToken(accessToken)
                .build();

        sendEmail(sendEmailReq);

        return seller;

    }

    public Map<String,String> login(SellerLoginReq sellerLoginReq) {
        Optional<Seller> seller = sellerRepository.findByEmail(sellerLoginReq.getEmail());

        if (seller.isPresent()) {
            if (passwordEncoder.matches(sellerLoginReq.getPassword(), seller.get().getPassword())) {

                Map<String, String> response = new HashMap<>();
                response.put("token", JwtUtils.generateAccessToken(seller.get(), secretKey, expiredTimeMs));
                return response;

            }else {
                return null;
            }
        }
        return null;
    }

    public Map<String,String> login(MemberLoginReq memberLoginReq) {
        Optional<Member> member = memberRepository.findByEmail(memberLoginReq.getEmail());

        if (member.isPresent()) {
            if (passwordEncoder.matches(memberLoginReq.getPassword(), member.get().getPassword())) {

                Map<String, String> response = new HashMap<>();
                response.put("token", JwtUtils.generateAccessToken(member.get(), secretKey, expiredTimeMs));
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
                + "&jwt=" + sendEmailReq.getAccessToken()
        );
        emailSender.send(message);
        emailVerifyService.create(sendEmailReq.getEmail(), uuid);
    }


    public Member getMemberByConsumerID(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    // TODO: 수정이 필요한 코드
    public Seller getMemberBySellerID(String email) {
        Optional<Seller> result = sellerRepository.findByEmail(email);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByEmail(email);
        Member member = null;
        if (result.isPresent())
            member = result.get();

        return member;
    }
}
