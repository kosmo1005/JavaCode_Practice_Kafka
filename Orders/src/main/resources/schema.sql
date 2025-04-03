CREATE TABLE IF NOT EXISTS user_order (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    good_name VARCHAR(255) NOT NULL,
    price DECIMAL(19,2),
    is_paid BOOLEAN NOT NULL
    );