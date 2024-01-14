package com.example.miniprojecttest.category.repository;

import com.example.miniprojecttest.category.model.entity.Category;
import com.example.miniprojecttest.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Optional<Category> findByRegion(String region);
//
//    Optional<Category> findByType(String type);
//
//    List<Category> findByAllRegion(String region);
}
