package com.example.miniprojecttest.cart.controller;

import com.example.miniprojecttest.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @RequestMapping(method = RequestMethod.POST, value = "/in/{productIdx}")
    public ResponseEntity<Object> cartIn(@AuthenticationPrincipal String email, @PathVariable Long productIdx) {
        cartService.cartIn(productIdx, email);
        return ResponseEntity.ok().body("ok");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cartList")
    public ResponseEntity<Object> cartList(@AuthenticationPrincipal String email) {
        cartService.cartList(email);
        return ResponseEntity.ok().body("ok");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateCart/{cartIdx}")
    public ResponseEntity<Object> updateCart(@RequestHeader(value = "Authorization") String token, Long cartIdx) {
        cartService.updateCart(token, cartIdx);
        return ResponseEntity.ok().body("ok");
    }
}
