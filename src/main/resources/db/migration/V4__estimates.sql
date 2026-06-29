CREATE TABLE work_order_diagnoses (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT NOT NULL REFERENCES work_orders(id),

    description TEXT NOT NULL,
    notes TEXT,

    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_diagnosis_wo ON work_order_diagnoses(work_order_id);

CREATE TABLE work_order_estimates (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT NOT NULL UNIQUE REFERENCES work_orders(id),

    total_service NUMERIC(12,2) DEFAULT 0,
    total_part NUMERIC(12,2) DEFAULT 0,
    grand_total NUMERIC(12,2) DEFAULT 0,

    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',

    created_at TIMESTAMP DEFAULT NOW(),
    approved_at TIMESTAMP
);

CREATE TABLE estimate_services (
    id BIGSERIAL PRIMARY KEY,
    estimate_id BIGINT NOT NULL REFERENCES work_order_estimates(id),

    service_name VARCHAR(100),
    price NUMERIC(12,2) NOT NULL
);

CREATE TABLE estimate_parts (
    id BIGSERIAL PRIMARY KEY,
    estimate_id BIGINT NOT NULL REFERENCES work_order_estimates(id),

    part_id BIGINT NOT NULL,

    qty INT NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    subtotal NUMERIC(12,2) NOT NULL
);