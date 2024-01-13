package com.example.miniprojecttest.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    @RequestMapping(method = RequestMethod.GET, value = "/selectCategory")
    public void selectCategory() {

    }
}
