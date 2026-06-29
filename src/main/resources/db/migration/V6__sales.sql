CREATE TABLE sales (
    id BIGSERIAL PRIMARY KEY,

    invoice_number VARCHAR(30) UNIQUE,

    customer_id BIGINT,

    total_amount NUMERIC(12,2) DEFAULT 0,

    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE sale_items (
    id BIGSERIAL PRIMARY KEY,

    sale_id BIGINT NOT NULL REFERENCES sales(id),
    part_id BIGINT NOT NULL,

    qty INT NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    subtotal NUMERIC(12,2) NOT NULL
);