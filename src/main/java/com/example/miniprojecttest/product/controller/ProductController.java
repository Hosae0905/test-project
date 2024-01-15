package com.example.miniprojecttest.product.controller;

import com.example.miniprojecttest.product.model.request.PatchProductUpdateReq;
import com.example.miniprojecttest.product.model.request.PostProductRegisterReq;
import com.example.miniprojecttest.product.model.response.PostProductResgisterRes;
import com.example.miniprojecttest.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity register(@AuthenticationPrincipal String email, @RequestPart PostProductRegisterReq productRegisterReq, @RequestPart MultipartFile[] images) {
        PostProductResgisterRes postProductResgisterRes = productService.register(email,productRegisterReq,images);
        return ResponseEntity.ok(postProductResgisterRes);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(@AuthenticationPrincipal String email,Integer page, Integer size) {


        return ResponseEntity.ok().body(productService.list(email,page, size));
    }
    @GetMapping("/read/{idx}")
    public ResponseEntity read(@AuthenticationPrincipal String email,@PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.read(email,idx));

    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(@AuthenticationPrincipal String email,PatchProductUpdateReq patchProductUpdateReq) {
        productService.update(email,patchProductUpdateReq);

        return ResponseEntity.ok().body("수정");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{idx}")
    public ResponseEntity delete(@AuthenticationPrincipal String email,@PathVariable Long idx) {
        productService.delete(email,idx);
        return ResponseEntity.ok().body("삭제");

    }
}
