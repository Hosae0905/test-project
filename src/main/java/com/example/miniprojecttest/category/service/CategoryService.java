package com.example.miniprojecttest.category.service;

import com.example.miniprojecttest.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void selectCategory() {

    }
}
