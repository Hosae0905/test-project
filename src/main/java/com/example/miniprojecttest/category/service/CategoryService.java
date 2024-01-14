package com.example.miniprojecttest.category.service;

import com.example.miniprojecttest.category.model.entity.Category;
import com.example.miniprojecttest.category.model.entity.CategoryToProduct;
import com.example.miniprojecttest.category.model.request.PostInsertCategoryReq;
import com.example.miniprojecttest.category.model.response.GetSearchRes;
import com.example.miniprojecttest.category.model.response.PostInsertRes;
import com.example.miniprojecttest.category.repository.CategoryRepository;
import com.example.miniprojecttest.category.repository.CategoryToProductRepository;
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
    private final CategoryToProductRepository categoryToProductRepository;

    public List<GetSearchRes> searchRegion(String region) {

        List<CategoryToProduct> category = categoryToProductRepository.findALlByCategory(Category.builder().region(region).build());
        List<GetSearchRes> searchList = new ArrayList<>();

        for (CategoryToProduct categoryToProduct : category) {
            searchList.add(GetSearchRes.builder()
                    .productIdx(categoryToProduct.getProduct().getIdx())
                    .productName(categoryToProduct.getProduct().getProductName())
                    .price(categoryToProduct.getProduct().getPrice())
                    .image(categoryToProduct.getProduct().getImages().get(0).getImagePath())
                    .sellerIdx(categoryToProduct.getProduct().getSellerIdx().getSellerIdx())
                    .build());
        }

        return searchList;
    }

    public PostInsertRes insertCategory(Long idx, PostInsertCategoryReq categoryReq) {

        Optional<Product> product = productRepository.findById(idx);

        if (product.isPresent()) {
            Category category = categoryRepository.save(Category.builder()
                    .region(Region.findRegion().get(categoryReq.getRegionId()))
                    .type(ProductType.findType().get(categoryReq.getTypeId()))
                    .build());

            categoryToProductRepository.save(CategoryToProduct.builder()
                    .category(category)
                    .product(product.get())
                    .build());

            return PostInsertRes.builder().code(1000).build();
        } else {
            return null;
        }
    }
}
