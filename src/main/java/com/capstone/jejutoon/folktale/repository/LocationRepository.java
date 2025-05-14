package com.capstone.jejutoon.folktale.repository;

import com.capstone.jejutoon.folktale.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT l " +
            "FROM Location l " +
            "WHERE l.folktale.id IN :folktaleIds")
    List<Location> findByFolktaleIdIn(@Param("folktaleIds") List<Long> folktaleIds);

    @Query("SELECT l " +
            "FROM Location l " +
            "WHERE l.folktale.id = :folktaleId")
    List<Location> findByFolktaleId(@Param("folktaleId") Long folktaleId);
}
