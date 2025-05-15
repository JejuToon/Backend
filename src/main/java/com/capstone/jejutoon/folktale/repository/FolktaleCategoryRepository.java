package com.capstone.jejutoon.folktale.repository;

import com.capstone.jejutoon.folktale.domain.FolktaleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolktaleCategoryRepository extends JpaRepository<FolktaleCategory, Long> {

    @Query("SELECT c.name " +
            "FROM FolktaleCategory fc " +
            "JOIN fc.category c " +
            "WHERE fc.folktale.id = :folktaleId")
    List<String> findCategoryNamesByFolktaleId(@Param("folktaleId") Long folktaleId);
}
