package com.bankstatement.ai.repository;

import com.bankstatement.ai.entity.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Long> {

    List<AnalysisResult> findByStatementId(Long statementId);

    List<AnalysisResult> findByStatementIdAndIsAnomalyTrue(Long statementId);
}
