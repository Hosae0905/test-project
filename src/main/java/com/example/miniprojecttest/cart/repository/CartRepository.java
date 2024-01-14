package com.example.miniprojecttest.cart.repository;

import com.example.miniprojecttest.cart.model.entity.Cart;
import com.example.miniprojecttest.member.model.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMember(Consumer member);
}
