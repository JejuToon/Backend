package com.capstone.jejutoon.folktale.repository;

import com.capstone.jejutoon.folktale.domain.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    @Query("SELECT s " +
            "FROM Scenario s " +
            "WHERE s.folktaleDetail.id = :folktaleDetailId")
    Optional<Scenario> findByFolktaleDetailId(@Param("folktaleDetailId") Long folktaleDetailId);

    @Query("SELECT c.answer " +
            "FROM Scenario s " +
            "JOIN Choice c ON s.id = c.scenario.id " +
            "WHERE s.id = :scenarioId")
    List<String> findChoicesById(@Param("scenarioId") Long scenarioId);
}
