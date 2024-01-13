package com.example.miniprojecttest.product.controller;

import com.example.miniprojecttest.product.model.request.PostProductRegisterReq;
import com.example.miniprojecttest.product.model.request.PostProductResgisterRes;
import com.example.miniprojecttest.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity register(Principal principal, @RequestPart PostProductRegisterReq productRegisterReq, @RequestPart MultipartFile[] images) {

        PostProductResgisterRes postProductResgisterRes = productService.register(principal,productRegisterReq,images);

        return ResponseEntity.ok(postProductResgisterRes);

    }

    /* @RequestMapping(method = RequestMethod.POST, value = "/list")
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
    }*/
}
