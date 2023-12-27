CREATE DATABASE IF NOT EXISTS meliordb;

ALTER DATABASE meliordb
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

--Replace 'melioradmin' with your corresponding MySQL server's username
GRANT ALL PRIVILEGES ON meliordb.* TO 'melioradmin';

USE meliordb;

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS fragrances;
DROP TABLE IF EXISTS customers;

CREATE TABLE fragrances (
    id      INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(80)
);
CREATE INDEX fragrances_name ON fragrances (name);

CREATE TABLE customers (
   id           INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
   first_name   VARCHAR(30),
   last_name    VARCHAR(30),
   address      VARCHAR(255),
   city         VARCHAR(80),
   telephone    VARCHAR(20),
   email        VARCHAR(255)
);
CREATE INDEX customers_last_name ON customers (last_name);

CREATE TABLE orders (
    id                  INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    quantity            INT(4) UNSIGNED NOT NULL,
    creation_date       DATE,
    fragrance_type_id   INT(4) UNSIGNED NOT NULL,
    customer_id         INT(4) UNSIGNED NOT NULL
);
ALTER TABLE orders ADD CONSTRAINT fk_customers FOREIGN KEY (customer_id) REFERENCES customers (id);
ALTER TABLE orders ADD CONSTRAINT fk_fragrance_type FOREIGN KEY (fragrance_type_id) REFERENCES fragrances (id);