package com.example.miniprojecttest.product.service;

import com.example.miniprojecttest.product.repository.ProductImageRepository;
import com.example.miniprojecttest.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final ImageSaveService imageSaveService;

    // TODO: 상품 등록
    public void register() {
        // 상품 저장
//        productRepository.save();

        // 상품 이미지 S3에 업로드
//        imageSaveService.saveFile();

        // 상품 이미지 저장
//        imageRepository.save();

    }

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
}
