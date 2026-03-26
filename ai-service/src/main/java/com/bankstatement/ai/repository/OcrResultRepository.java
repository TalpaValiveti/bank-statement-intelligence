package com.bankstatement.ai.repository;

import com.bankstatement.ai.entity.OcrResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OcrResultRepository extends JpaRepository<OcrResult, Long> {

    Optional<OcrResult> findByStatementId(Long statementId);
}
