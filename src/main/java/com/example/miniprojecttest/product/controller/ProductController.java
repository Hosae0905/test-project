package com.example.miniprojecttest.product.controller;

import com.example.miniprojecttest.product.model.request.PostProductRegisterReq;
import com.example.miniprojecttest.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public void register(
            Authentication authentication,
            @RequestPart PostProductRegisterReq productRegisterReq,
            @RequestPart MultipartFile[] images) {

        productService.register();

    }

    @RequestMapping(method = RequestMethod.POST, value = "/list")
    public void list() {
        productService.list();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{idx}")
    public void getProduct(@PathVariable Long idx) {
        productService.getProduct(idx);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update/{idx}")
    public void update(@PathVariable Long idx) {
        productService.update(idx);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete/{idx}")
    public void delete(@PathVariable Long idx) {
        productService.delete(idx);
    }
}
