package com.capstone.jejutoon.folktale.repository;

import com.capstone.jejutoon.folktale.domain.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    @Query("SELECT c " +
            "FROM Choice c " +
            "WHERE c.folktaleDetail.id = :folktaleDetailId")
    List<Choice> findChoicesByFolktaleDetailId(@Param("folktaleDetailId") Long folktaleDetailId);
}
