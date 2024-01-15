package com.example.miniprojecttest.product.repository;

import com.example.miniprojecttest.product.model.entity.Product;
import com.example.miniprojecttest.product.repository.querydsl.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    public Optional<Product> findByproductName(String name);

}
