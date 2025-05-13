package com.capstone.jejutoon.folktale.repository;

import com.capstone.jejutoon.folktale.domain.Folktale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolktaleRepository extends JpaRepository<Folktale, Long> {
}
