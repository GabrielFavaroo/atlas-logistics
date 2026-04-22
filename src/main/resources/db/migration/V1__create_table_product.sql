
CREATE TABLE product (
    id uuid PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    sku VARCHAR(255) NOT NULL UNIQUE,
    value numeric(38,2) NOT NULL

);





