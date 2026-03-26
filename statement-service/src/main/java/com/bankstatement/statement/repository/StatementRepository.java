package com.bankstatement.statement.repository;

import com.bankstatement.statement.entity.Statement;
import com.bankstatement.statement.entity.StatementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

    List<Statement> findByUserId(Long userId);

    List<Statement> findByUserIdAndStatus(Long userId, StatementStatus status);

    List<Statement> findByStatus(StatementStatus status);
}
