DROP TABLE orders IF EXISTS;
DROP TABLE fragrances IF EXISTS;
DROP TABLE customers IF EXISTS;

CREATE TABLE fragrances (
    id   INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(80)
);
CREATE INDEX fragrances_name ON fragrances (name);

CREATE TABLE customers (
    id          INTEGER IDENTITY PRIMARY KEY,
    first_name  VARCHAR(30),
    last_name   VARCHAR_IGNORECASE(30),
    address     VARCHAR(255),
    city        VARCHAR(80),
    telephone   VARCHAR(20),
    email       VARCHAR(255)
);
CREATE INDEX customers_last_name ON customers (last_name);

CREATE TABLE orders (
    id                  INTEGER IDENTITY PRIMARY KEY,
    quantity            INTEGER NOT NULL,
    creation_date       DATE,
    fragrance_type_id   INTEGER NOT NULL,
    customer_id         INTEGER NOT NULL
);
ALTER TABLE orders ADD CONSTRAINT fk_customers FOREIGN KEY (customer_id) REFERENCES customers (id);
ALTER TABLE orders ADD CONSTRAINT fk_fragrance_type FOREIGN KEY (fragrance_type_id) REFERENCES fragrances (id);
