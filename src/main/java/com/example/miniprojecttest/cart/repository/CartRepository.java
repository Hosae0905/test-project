package com.example.miniprojecttest.cart.repository;

import com.example.miniprojecttest.cart.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
