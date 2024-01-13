package com.example.miniprojecttest.cart.service;

import com.example.miniprojecttest.cart.repository.CartRepository;
import com.example.miniprojecttest.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    public void cartIn(Long productIdx, Authentication authentication) {

    }

    public void cartList(Authentication authentication) {

    }

    public void updateCart(Long productIdx, Authentication authentication) {

    }

    public void deleteCart(Authentication authentication) {

    }

}
