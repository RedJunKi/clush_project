package com.clush.test.member.repository;

import com.clush.test.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Override
    Optional<Member> findById(Long aLong);
}
