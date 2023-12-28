DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS fragrances;
DROP TABLE IF EXISTS customers;

CREATE TABLE fragrances (
    id   SERIAL PRIMARY KEY,
    name VARCHAR(80)
);
CREATE INDEX IF NOT EXISTS fragrances_name ON fragrances (name);

CREATE TABLE customers (
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(30),
    last_name   VARCHAR(30),
    address     VARCHAR(255),
    city        VARCHAR(80),
    telephone   VARCHAR(20),
    email       VARCHAR(255)
);
CREATE INDEX IF NOT EXISTS customers_last_name ON customers (last_name);

CREATE TABLE orders (
    id                  SERIAL PRIMARY KEY,
    quantity            INTEGER NOT NULL,
    creation_date       DATE,
    fragrance_type_id   INTEGER NOT NULL,
    customer_id         INTEGER NOT NULL
);
ALTER TABLE orders ADD CONSTRAINT fk_customers FOREIGN KEY (customer_id) REFERENCES customers (id);
ALTER TABLE orders ADD CONSTRAINT fk_fragrance_type FOREIGN KEY (fragrance_type_id) REFERENCES fragrances (id);
