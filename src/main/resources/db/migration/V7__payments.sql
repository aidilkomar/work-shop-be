CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,

    reference_type VARCHAR(20) NOT NULL,
    reference_id BIGINT NOT NULL,

    amount NUMERIC(12,2) NOT NULL,

    method VARCHAR(20) NOT NULL,

    paid_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_payment_ref ON payments(reference_type, reference_id);