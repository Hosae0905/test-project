package com.example.miniprojecttest.product.repository;

import com.example.miniprojecttest.product.model.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
