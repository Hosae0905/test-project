package com.example.miniprojecttest.member.repository;

import com.example.miniprojecttest.member.model.entity.Member;
import com.example.miniprojecttest.member.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findBySellerID(String sellerID);
}
