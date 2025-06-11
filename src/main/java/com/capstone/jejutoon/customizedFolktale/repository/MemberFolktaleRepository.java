package com.capstone.jejutoon.customizedFolktale.repository;

import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberFolktaleRepository extends JpaRepository<MemberFolktale, Long> {

    @EntityGraph(attributePaths = {"folktale"})
    @Query(value = "SELECT DISTINCT mf " +
            "FROM MemberFolktale mf " +
            "WHERE mf.member.id = :memberId",
    countQuery = "SELECT COUNT(mf) " +
            "FROM MemberFolktale mf " +
            "WHERE mf.member.id = :memberId")
    Page<MemberFolktale> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT f.prompt " +
            "FROM MemberFolktale mf " +
            "JOIN Folktale f ON mf.folktale.id = f.id " +
            "WHERE mf.id = :memberFolktaleId")
    String findPromptById(@Param("memberFolktaleId") Long memberFolktaleId);
}
