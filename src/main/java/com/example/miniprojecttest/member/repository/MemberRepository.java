package com.example.miniprojecttest.member.repository;

import com.example.miniprojecttest.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByConsumerID(String consumerID);
}
