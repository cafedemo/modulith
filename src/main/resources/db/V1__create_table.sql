CREATE SCHEMA IF NOT EXISTS cafe;
CREATE TABLE IF NOT EXISTS shop (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT unique_name_email UNIQUE (name, email)
);
