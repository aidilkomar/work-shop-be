CREATE TABLE parts (
    id BIGSERIAL PRIMARY KEY,

    sku VARCHAR(50) UNIQUE,
    name VARCHAR(100) NOT NULL,

    purchase_price NUMERIC(12,2) DEFAULT 0,
    selling_price NUMERIC(12,2) DEFAULT 0,

    stock INT DEFAULT 0,
    min_stock INT DEFAULT 0
);

CREATE INDEX idx_parts_name ON parts(name);

CREATE TABLE stock_movements (
    id BIGSERIAL PRIMARY KEY,

    part_id BIGINT NOT NULL REFERENCES parts(id),

    type VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,

    reference_type VARCHAR(20),
    reference_id BIGINT,

    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_stock_part ON stock_movements(part_id);