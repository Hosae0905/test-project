package com.example.miniprojecttest.member.service;


import com.example.miniprojecttest.member.model.entity.EmailVerify;
import com.example.miniprojecttest.member.model.entity.Member;
import com.example.miniprojecttest.member.model.entity.Seller;
import com.example.miniprojecttest.member.model.request.GetEmailConfirmReq;
import com.example.miniprojecttest.member.repository.EmailVerifyRepository;
import com.example.miniprojecttest.member.repository.MemberRepository;
import com.example.miniprojecttest.member.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final EmailVerifyRepository emailVerifyRepository;
    private final MemberRepository memberRepository;
    private final SellerRepository sellerRepository;

    public RedirectView verify(GetEmailConfirmReq getEmailConfirmReq) {
        Optional<EmailVerify> result = emailVerifyRepository.findByEmail(getEmailConfirmReq.getEmail());
        if(result.isPresent()){
            EmailVerify emailVerify = result.get();
            if(emailVerify.getUuid().equals(getEmailConfirmReq.getToken())) {
                update(getEmailConfirmReq.getEmail(), getEmailConfirmReq.getAuthority());
                return new RedirectView("http://localhost:3000/emailconfirm/" + getEmailConfirmReq.getJwt());
            }
        }
        return new RedirectView("http://localhost:3000/emailCertError");
    }

    public void update(String email, String authority) {
        if (authority.equals("CONSUMER")){
            Optional<Member> result = memberRepository.findByEmail(email);
            if(result.isPresent()) {
                Member member = result.get();
                member.setStatus(true);
                memberRepository.save(member);
            }
        }else if (authority.equals("SELLER")){
            Optional<Seller> result = sellerRepository.findByEmail(email);
            if(result.isPresent()) {
                Seller seller = result.get();
                seller.setStatus(true);
                sellerRepository.save(seller);
            }
        }
    }
    public void create(String email, String uuid) {
        EmailVerify emailVerify = EmailVerify.builder()
                .email(email)
                .uuid(uuid)
                .build();
        emailVerifyRepository.save(emailVerify);
    }
}
