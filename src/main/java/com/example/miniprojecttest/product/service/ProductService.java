package com.example.miniprojecttest.product.service;

import com.example.miniprojecttest.member.model.entity.Seller;
import com.example.miniprojecttest.member.repository.SellerRepository;
import com.example.miniprojecttest.product.model.entity.Product;
import com.example.miniprojecttest.product.model.request.PostProductRegisterReq;
import com.example.miniprojecttest.product.model.request.PostProductResgisterRes;
import com.example.miniprojecttest.product.repository.ProductImageRepository;
import com.example.miniprojecttest.product.repository.ProductRepository;
import com.example.miniprojecttest.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageSaveService imageSaveService;
    private final SellerRepository sellerRepository;


    @Value("${jwt.secret-key}")
    private String secretKey;



    // TODO: 상품 등록
    public PostProductResgisterRes register(String email, PostProductRegisterReq productRegisterReq, MultipartFile[] images) {

        Optional<Seller> seller = sellerRepository.findByEmail(email);

        Seller user;
        if (seller.isPresent()) {
            user = seller.get();
        } else throw new RuntimeException("비인증된 접근입니다.");

        Product product;
        product = productRepository.save(Product.builder()
                .productName(productRegisterReq.getProductName())
                .productInfo(productRegisterReq.getProductInfo())
                .sellerIdx(user)
                .build());

        for (MultipartFile uploadFile : images) {
            String uploadPath = imageSaveService.uploadFile(uploadFile);
            imageSaveService.saveFile(product.getIdx(), uploadPath);
        }
        return PostProductResgisterRes.EntityToDto(product);

    }
    /*
    // TODO: 상품 전체 조회
    public void list() {

        // 상품 전체 조회
//        productRepository.findAll();
    }

    // TODO: 상품 하나 조회
    public void getProduct(Long idx) {
        // 상품 하나 가져오기
//        productRepository.findById(idx);
    }


    // TODO: 상품 수정
    public void update(Long idx) {

        // 받아온 idx 값에 해당하는 상품 조회
//        productRepository.findById(idx);

        // 상품 수정

        // 수정한 상품 정보를 다시 저장(update)
//        productRepository.save();
    }

    // TODO: 상품 삭제
    public void delete(Long idx) {

        // 받아온 idx 값에 해당하는 상품 삭제
//        productRepository.deleteById(idx);
    }
     */
}

