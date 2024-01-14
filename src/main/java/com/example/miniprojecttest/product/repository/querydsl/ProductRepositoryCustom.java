package com.example.miniprojecttest.product.repository.querydsl;
import com.example.miniprojecttest.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    public Page<Product> findList(Pageable pageable);
}
