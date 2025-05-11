package com.capstone.jejutoon.member.repository;

import com.capstone.jejutoon.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m " +
            "FROM Member m " +
            "WHERE m.loginId = :loginId")
    Optional<Member> findByLoginId(@Param("loginId") String loginId);
}
