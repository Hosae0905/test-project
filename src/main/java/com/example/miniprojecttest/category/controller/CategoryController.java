package com.example.miniprojecttest.category.controller;

import com.example.miniprojecttest.category.model.request.PostInsertCategoryReq;
import com.example.miniprojecttest.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/search/{idx}")
    public ResponseEntity<Object> searchCategory(@PathVariable Long idx) {
        return ResponseEntity.ok().body(categoryService.searchCategory(idx));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/insert/{idx}")
    public ResponseEntity<Object> insertCategory(@PathVariable Long idx, @RequestBody PostInsertCategoryReq categoryReq) {
        return ResponseEntity.ok().body(categoryService.insertCategory(idx, categoryReq));
    }
}
