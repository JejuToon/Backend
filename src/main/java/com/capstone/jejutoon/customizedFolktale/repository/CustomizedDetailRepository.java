package com.capstone.jejutoon.customizedFolktale.repository;

import com.capstone.jejutoon.customizedFolktale.domain.CustomizedDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomizedDetailRepository extends JpaRepository<CustomizedDetail, Long> {
}
