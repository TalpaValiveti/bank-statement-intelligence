package com.bankstatement.alert.repository;

import com.bankstatement.alert.entity.Alert;
import com.bankstatement.alert.entity.AlertSeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByUserId(Long userId);

    List<Alert> findByUserIdAndIsSentFalse(Long userId);

    List<Alert> findByUserIdAndSeverity(Long userId, AlertSeverity severity);

    List<Alert> findByStatementId(Long statementId);
}
