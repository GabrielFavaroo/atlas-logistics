
CREATE TABLE stock (
    id uuid PRIMARY KEY NOT NULL,
    quantity integer NOT NULL,
    product_id uuid NOT NULL,
    warehouse_id uuid NOT NULL,
    CONSTRAINT product_id
        FOREIGN KEY(product_id)
        REFERENCES product(id),

    CONSTRAINT warehouse_id
        FOREIGN KEY(warehouse_id)
        REFERENCES warehouse(id)
);




