package com.capstone.jejutoon.folktale.repository;

import com.capstone.jejutoon.folktale.domain.Folktale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
