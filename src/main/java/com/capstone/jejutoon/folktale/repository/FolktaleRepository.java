package com.capstone.jejutoon.folktale.repository;

import com.capstone.jejutoon.folktale.domain.Folktale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolktaleRepository extends JpaRepository<Folktale, Long> {

    @Query(
            value = "SELECT f " +
                    "FROM Folktale f " +
                    "JOIN FolktaleCategory fc ON f.id = fc.folktale.id " +
                    "WHERE fc.category.name = :category",
            countQuery = "SELECT COUNT(f) " +
                    "FROM Folktale f " +
                    "JOIN FolktaleCategory fc ON f.id = fc.folktale.id " +
                    "WHERE fc.category.name = :category"
    )
    Page<Folktale> findFolktalesByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT AVG(mf.score) " +
            "FROM Folktale f " +
            "JOIN MemberFolktale mf ON f.id = mf.folktale.id " +
            "WHERE f.id = :folktaleId")
    Double findAverageScoreByFolktaleId(@Param("folktaleId") Long folktaleId);

    @Query("SELECT f.id, AVG(mf.score) " +
            "FROM Folktale f " +
            "JOIN MemberFolktale mf ON f.id = mf.folktale.id " +
            "WHERE f.id IN :folktaleIds " +
            "GROUP BY f.id")
    List<Object[]> findAverageScoresByFolktaleIds(@Param("folktaleIds") List<Long> folktaleIds);

    @Query(
            value = "SELECT f " +
                    "FROM Folktale f " +
                    "WHERE f.title LIKE :title",
            countQuery = "SELECT COUNT(f) " +
                    "FROM Folktale f " +
                    "WHERE f.title LIKE :title"
    )
    Page<Folktale> findByTitle(@Param("title") String title, Pageable pageable);
}
