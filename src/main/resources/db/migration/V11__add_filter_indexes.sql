CREATE INDEX idx_product_category ON product(category_id);
CREATE INDEX idx_product_brand ON product(LOWER(brand));
CREATE INDEX idx_product_price ON product(price);
CREATE INDEX idx_product_quantity ON product(quantity) WHERE quantity > 0;