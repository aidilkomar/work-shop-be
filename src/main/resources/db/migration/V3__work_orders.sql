CREATE TABLE work_orders (
    id BIGSERIAL PRIMARY KEY,
    wo_number VARCHAR(30) NOT NULL UNIQUE,

    customer_id BIGINT NOT NULL REFERENCES customers(id),
    vehicle_id BIGINT NOT NULL REFERENCES vehicles(id),
    mechanic_id BIGINT REFERENCES mechanics(id),

    type VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,

    complaint TEXT,

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_work_orders_status ON work_orders(status);
CREATE INDEX idx_work_orders_vehicle ON work_orders(vehicle_id);