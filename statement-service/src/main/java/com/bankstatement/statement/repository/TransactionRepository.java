package com.bankstatement.statement.repository;

import com.bankstatement.statement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByStatementId(Long statementId);

    List<Transaction> findByStatementIdAndCategory(Long statementId, String category);
}
