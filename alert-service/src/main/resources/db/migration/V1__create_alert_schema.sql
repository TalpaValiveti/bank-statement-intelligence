CREATE SCHEMA IF NOT EXISTS alert;

CREATE TABLE alert.alerts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    statement_id BIGINT,
    transaction_id BIGINT,
    alert_type VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    severity VARCHAR(20) NOT NULL DEFAULT 'INFO',
    is_sent BOOLEAN NOT NULL DEFAULT FALSE,
    sent_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_alerts_user_id ON alert.alerts(user_id);
CREATE INDEX idx_alerts_statement_id ON alert.alerts(statement_id);
