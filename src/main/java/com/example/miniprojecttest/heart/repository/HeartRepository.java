package com.example.miniprojecttest.heart.repository;

import com.example.miniprojecttest.heart.model.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
}
