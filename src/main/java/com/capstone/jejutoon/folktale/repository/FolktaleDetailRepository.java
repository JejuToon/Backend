package com.capstone.jejutoon.folktale.repository;

import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import com.capstone.jejutoon.folktale.domain.FolktaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolktaleDetailRepository extends JpaRepository<FolktaleDetail, Long> {

    @Query("SELECT fd.id " +
            "FROM FolktaleDetail fd " +
            "WHERE fd.folktale.id = :folktaleId")
    List<Long> findByFolktaleId(@Param("folktaleId") Long folktaleId);

    @Query("SELECT mf " +
            "FROM FolktaleDetail fd " +
            "JOIN Folktale f ON fd.folktale.id = f.id " +
            "JOIN FETCH MemberFolktale mf ON mf.folktale.id = f.id " +
            "WHERE fd.id = :folktaleDetailId")
    Optional<MemberFolktale> findMemberFolktaleByFolktaleDetailId(@Param("folktaleDetailId") Long folktaleDetailId);
}
