package com.example.miniprojecttest.cart.service;

import com.example.miniprojecttest.cart.model.entity.Cart;
import com.example.miniprojecttest.cart.model.response.GetCartListRes;
import com.example.miniprojecttest.cart.repository.CartRepository;
import com.example.miniprojecttest.member.model.entity.Consumer;
import com.example.miniprojecttest.member.repository.ConsumerRepository;
import com.example.miniprojecttest.product.model.entity.Product;
import com.example.miniprojecttest.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ConsumerRepository memberRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public void cartIn(Long productIdx, String email) {
        Optional<Consumer> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            cartRepository.save(Cart.builder()
                    .product(Product.builder().idx(productIdx).build())
                    .member(member.get())
                    .build());
        }
    }

    public void cartList(String email) {

        Optional<Consumer> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            List<Cart> carts = cartRepository.findAllByMember(Consumer.builder().consumerIdx(member.get().getConsumerIdx()).build());
            List<GetCartListRes> cartList = new ArrayList<>();

            for (Cart cart : carts) {
                Product product = cart.getProduct();
                cartList.add(GetCartListRes.builder()
                        .idx(cart.getIdx())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .image(product.getImages().get(0).getImagePath())
                        .build());
            }
        }
    }

    public void updateCart(String token, Long cartIdx) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.split(" ")[1];
        }

        Long memberIdx = JwtUtils.getUserIdx(token, secretKey);

        Optional<Consumer> member = memberRepository.findById(memberIdx);

        if (member.isPresent()) {
            cartRepository.deleteById(cartIdx);
        }


    }
}
