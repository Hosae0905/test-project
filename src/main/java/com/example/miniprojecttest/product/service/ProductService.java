package com.example.miniprojecttest.product.service;

import com.example.miniprojecttest.member.model.entity.Seller;
import com.example.miniprojecttest.member.repository.SellerRepository;
import com.example.miniprojecttest.product.model.entity.Product;
import com.example.miniprojecttest.product.model.entity.ProductImage;
import com.example.miniprojecttest.product.model.request.PatchProductUpdateReq;
import com.example.miniprojecttest.product.model.request.PostProductRegisterReq;
import com.example.miniprojecttest.product.model.response.GetProductListRes;
import com.example.miniprojecttest.product.model.response.GetProductReadRes;
import com.example.miniprojecttest.product.model.response.GetProductReadRes2;
import com.example.miniprojecttest.product.model.response.PostProductResgisterRes;
import com.example.miniprojecttest.product.repository.ProductImageRepository;
import com.example.miniprojecttest.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ImageSaveService imageSaveService;
    private final SellerRepository sellerRepository;

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
                .price(productRegisterReq.getPrice())
                .salePrice(productRegisterReq.getSalePrice())
                .sellerIdx(user)
                .build());

        for (MultipartFile uploadFile : images) {
            String uploadPath = imageSaveService.uploadFile(uploadFile);
            imageSaveService.saveFile(product.getIdx(), uploadPath);
        }
        return PostProductResgisterRes.EntityToDto(product);

    }

    // TODO: 상품 전체 조회
    public GetProductListRes list(String email,Integer page, Integer size) {

        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if (seller.isPresent()) {
            System.out.println("인증된 접근입니다.");
        } else throw new RuntimeException("비인증된 접근입니다.");

        Pageable pageable = PageRequest.of(page-1,size);
        Page<Product> result = productRepository.findList(pageable);

        List<GetProductReadRes> productReadResList = new ArrayList<>();

        for (Product product : result.getContent()) {

            List<ProductImage> productImages = product.getImages();

             String filenames = "";
            for (ProductImage productImage : productImages) {
                String filename = productImage.getImagePath();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);

            // DtoToRes
            GetProductReadRes getProductReadRes = GetProductReadRes.DtoToRes(product,filenames);
            productReadResList.add(getProductReadRes);
        }

            // DtoToRes
        return GetProductListRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(productReadResList)
                .build();
    }

    public GetProductReadRes2 read(String email,Long idx) {
        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if(seller.isPresent()) {
            System.out.println("인증된 접근입니다.");
        } else throw new RuntimeException("비인증된 접근입니다.");

        Optional<Product> result = productRepository.findById(idx);

        if (result.isPresent()) {
            Product product = result.get();

            List<ProductImage> productImages = product.getImages();

            String filenames = "";
            for (ProductImage productImage : productImages) {
                String filename = productImage.getImagePath();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            GetProductReadRes getProductReadRes = GetProductReadRes.builder()
                    .idx(product.getIdx())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .salePrice(product.getSalePrice())
                    .productInfo(product.getProductInfo())
                    .filename(filenames)
                    .sellerIdx(product.getSellerIdx())
                    .build();

            return GetProductReadRes2.builder()
                    .code(1000)
                    .message("요청 성공.")
                    .success(true)
                    .isSuccess(true)
                    .result(getProductReadRes)
                    .build();
        }
        return null;
    }

    public void update(String email, PatchProductUpdateReq patchProductUpdateReq){

        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if(seller.isPresent()) {
            System.out.println("인증된 접근입니다.");
        } else throw new RuntimeException("비인증된 접근입니다.");

        Optional<Product> result = productRepository.findById(patchProductUpdateReq.getId());
        if (result.isPresent()) {
            Product product = result.get();
            product.setProductName(patchProductUpdateReq.getProductName());
            product.setPrice(patchProductUpdateReq.getPrice());
            product.setSalePrice(patchProductUpdateReq.getSalePrice());
            product.setProductInfo(patchProductUpdateReq.getProductInfo());
            product.setImages(patchProductUpdateReq.getProductImages());
            productRepository.save(product);
        }
    }

    public void delete(String email, Long idx) {

        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if(seller.isPresent()) {
            System.out.println("인증된 접근입니다.");
        } else throw new RuntimeException("비인증된 접근입니다.");

        Optional<Product> product =  productRepository.findById(idx);
        if(product.isPresent()) {
            Product product1 = product.get();
            List<ProductImage> images = product1.getImages();
            for (int i = 0; i < images.size(); i++) {
                ProductImage image = images.get(i);
                productImageRepository.delete(image);
            }
            productRepository.deleteById(idx);
        }
    }
}

