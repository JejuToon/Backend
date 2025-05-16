package com.capstone.jejutoon.customizedFolktale.repository;

import com.capstone.jejutoon.customizedFolktale.domain.MemberFolktale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberFolktaleRepository extends JpaRepository<MemberFolktale, Long> {
}
