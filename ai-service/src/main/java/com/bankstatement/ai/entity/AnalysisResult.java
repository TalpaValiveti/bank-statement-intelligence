package com.bankstatement.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "analysis_results", schema = "ai")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "statement_id", nullable = false)
    private Long statementId;

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "sentiment", length = 20)
    private String sentiment;

    @Column(name = "anomaly_score", precision = 5, scale = 2)
    private BigDecimal anomalyScore;

    @Column(name = "is_anomaly")
    private Boolean isAnomaly = false;

    @Column(name = "analysed_at", nullable = false, updatable = false)
    private LocalDateTime analysedAt;

    @PrePersist
    protected void onCreate() {
        analysedAt = LocalDateTime.now();
    }
}
