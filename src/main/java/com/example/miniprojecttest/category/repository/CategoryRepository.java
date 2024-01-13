package com.example.miniprojecttest.category.repository;

import com.example.miniprojecttest.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
