package com.example.miniprojecttest.category.service;

import com.example.miniprojecttest.category.model.entity.Category;
import com.example.miniprojecttest.category.model.request.PostInsertCategoryReq;
import com.example.miniprojecttest.category.model.response.GetSearchRes;
import com.example.miniprojecttest.category.model.response.PostInsertRes;
import com.example.miniprojecttest.category.repository.CategoryRepository;
import com.example.miniprojecttest.product.model.entity.Product;
import com.example.miniprojecttest.product.repository.ProductRepository;
import com.example.miniprojecttest.utils.ProductType;
import com.example.miniprojecttest.utils.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<GetSearchRes> searchCategory(Long idx) {
        Optional<Category> category = categoryRepository.findById(idx);
        List<GetSearchRes> searchList = new ArrayList<>();

        if (category.isPresent()) {
            for (Product product : category.get().getProducts()) {
                searchList.add(GetSearchRes.builder()
                        .productIdx(product.getIdx())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .image(product.getImages().get(0).getImagePath())
                        .sellerIdx(product.getSellerIdx().getSellerIdx())
                        .build());
            }
            return searchList;

        } else {
            return null;
        }
    }

    public PostInsertRes insertCategory(Long idx, PostInsertCategoryReq categoryReq) {

        Optional<Product> product = productRepository.findById(idx);

        if (product.isPresent()) {
            Category category = categoryRepository.save(Category.builder()
                    .region(Region.findRegion().get(categoryReq.getRegionId()))
                    .type(ProductType.findType().get(categoryReq.getTypeId()))
                    .build());

            product.get().setCategory(category);
            productRepository.save(product.get());
            return PostInsertRes.builder().code(1000).build();
        } else {
            return null;
        }
    }
}
