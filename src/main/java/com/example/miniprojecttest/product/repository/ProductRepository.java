package com.example.miniprojecttest.product.repository;

import com.example.miniprojecttest.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
