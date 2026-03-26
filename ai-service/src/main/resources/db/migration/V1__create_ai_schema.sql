CREATE SCHEMA IF NOT EXISTS ai;

CREATE TABLE ai.ocr_results (
    id BIGSERIAL PRIMARY KEY,
    statement_id BIGINT NOT NULL,
    raw_text TEXT,
    confidence_score NUMERIC(5,2),
    processed_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE ai.analysis_results (
    id BIGSERIAL PRIMARY KEY,
    statement_id BIGINT NOT NULL,
    transaction_id BIGINT,
    category VARCHAR(100),
    sentiment VARCHAR(20),
    anomaly_score NUMERIC(5,2),
    is_anomaly BOOLEAN DEFAULT FALSE,
    analysed_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_ocr_results_statement_id ON ai.ocr_results(statement_id);
CREATE INDEX idx_analysis_results_statement_id ON ai.analysis_results(statement_id);
