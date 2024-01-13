package com.example.miniprojecttest.member.service;

import com.example.miniprojecttest.config.PasswordEncoderConfig;
import com.example.miniprojecttest.member.model.Member;
import com.example.miniprojecttest.member.model.MemberLoginReq;
import com.example.miniprojecttest.member.model.MemberLoginRes;
import com.example.miniprojecttest.member.model.MemberSignupReq;
import com.example.miniprojecttest.member.repository.MemberRepository;
import com.example.miniprojecttest.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender emailSender;
    private final EmailVerifyService emailVerifyService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    public void consumerSignup(MemberSignupReq memberSignupReq) {
        Member member = Member.builder()
                .consumerID(memberSignupReq.getConsumerID())
                .consumerPW(passwordEncoder.encode(memberSignupReq.getConsumerPW()))
                .consumerName(memberSignupReq.getConsumerName())
                .consumerAddr(memberSignupReq.getConsumerAddr())
                .consunmerPhoneNum(memberSignupReq.getConsunmerPhoneNum())
                .authority("CONSUMER")
                .socialLogin(false)
                .status(false)
                .build();

        member = memberRepository.save(member);



    }
//    public void sellerSignup(MemberSignupReq memberSignupReq) {
//        Member member = Member.builder()
//                .consumerID(memberSignupReq.getConsumerID())
//                .consumerPW(passwordEncoder.encode(memberSignupReq.getConsumerPW()))
//                .consumerName(memberSignupReq.getConsumerName())
//                .consumerAddr(memberSignupReq.getConsumerAddr())
//                .consunmerPhoneNum(memberSignupReq.getConsunmerPhoneNum())
//                .authority("SELLER")
//                .socialLogin(false)
//                .status(false)
//                .build();
//
//        member = memberRepository.save(member);
//
//    }

    public MemberLoginRes login(MemberLoginReq memberLoginReq) {
        String jwt = JwtUtils.generateAccessToken(memberLoginReq.getUsername(), secretKey, expiredTimeMs);
        MemberLoginRes memberLoginRes = MemberLoginRes.builder()
                .token(jwt).build();
        return memberLoginRes;
        }


    public void sendEmail(MemberSignupReq memberSignupReq) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(memberSignupReq.getConsumerID());
        message.setSubject("[심마켓] 이메일 인증");
        String uuid = UUID.randomUUID().toString();
        message.setText("http://localhost:8080/member/confirm?email="
                + memberSignupReq.getConsumerID()
                + "&token=" + uuid
                + "&jwt=" + JwtUtils.generateAccessToken(memberSignupReq.getConsumerID(), secretKey, expiredTimeMs)
        );
        emailSender.send(message);

        emailVerifyService.create(memberSignupReq.getConsumerID(), uuid);
    }
    public void update(String email) {
        Optional<Member> result = memberRepository.findByConsumerID(email);
        if(result.isPresent()) {
            Member member = result.get();
            member.setStatus(true);
            memberRepository.save(member);
        }
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
