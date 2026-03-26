CREATE SCHEMA IF NOT EXISTS statement;

CREATE TABLE statement.statements (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    file_type VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'UPLOADED',
    uploaded_at TIMESTAMP NOT NULL DEFAULT NOW(),
    processed_at TIMESTAMP
);

CREATE TABLE statement.transactions (
    id BIGSERIAL PRIMARY KEY,
    statement_id BIGINT NOT NULL REFERENCES statement.statements(id),
    transaction_date DATE,
    description VARCHAR(500),
    amount NUMERIC(15,2),
    transaction_type VARCHAR(20),
    category VARCHAR(100),
    balance NUMERIC(15,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_statements_user_id ON statement.statements(user_id);
CREATE INDEX idx_transactions_statement_id ON statement.transactions(statement_id);
