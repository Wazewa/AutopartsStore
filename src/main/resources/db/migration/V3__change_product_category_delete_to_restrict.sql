ALTER TABLE Product DROP CONSTRAINT product_category_id_fkey;

ALTER TABLE Product
    ADD CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
            REFERENCES Category(category_id)
            ON DELETE RESTRICT;