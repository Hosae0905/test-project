package com.example.miniprojecttest.cart.controller;

import com.example.miniprojecttest.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @RequestMapping(method = RequestMethod.POST, value = "/in/{productIdx}")
    public void cartIn(@PathVariable Long productIdx, Authentication authentication) {
        cartService.cartIn(productIdx, authentication);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cartList")
    public void cartList(Authentication authentication) {
        cartService.cartList(authentication);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateCart/{productIdx}")
    public void updateCart(Authentication authentication, Long productIdx) {
        cartService.updateCart(productIdx, authentication);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteCart")
    public void deleteCart(Authentication authentication) {
        cartService.deleteCart(authentication);
    }
}
