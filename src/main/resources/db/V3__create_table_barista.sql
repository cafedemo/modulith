CREATE TABLE barista (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    shop_id INTEGER NOT NULL,
    email VARCHAR(255) NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES shop(id),
    CONSTRAINT unique_name_email_barista UNIQUE (name, email)
);