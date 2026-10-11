INSERT INTO roles (role_code, role_description)
VALUES
    ('ROLE_ADMIN',    'Администратор с полным доступом'),
    ('ROLE_CUSTOMER', 'Клиент интернет-магазина')
ON CONFLICT (role_code) DO NOTHING;

ALTER TABLE customer RENAME TO users;
ALTER TABLE users RENAME COLUMN customer_id TO user_id;

ALTER TABLE users ALTER COLUMN phone DROP NOT NULL;

ALTER TABLE users ADD COLUMN role_id INTEGER;

UPDATE users u
SET role_id = cr.role_id
FROM customer_roles cr
WHERE cr.customer_id = u.user_id;

ALTER TABLE users ALTER COLUMN role_id SET NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT users_role_id_fkey
        FOREIGN KEY (role_id) REFERENCES roles(role_id);

ALTER TABLE orders RENAME COLUMN customer_id TO user_id;
ALTER TABLE cart   RENAME COLUMN customer_id TO user_id;
ALTER TABLE event  RENAME COLUMN customer_id TO user_id;

ALTER TABLE orders DROP CONSTRAINT orders_processed_by_admin_id_fkey;
ALTER TABLE orders RENAME COLUMN processed_by_admin_id TO processed_by_user_id;
ALTER TABLE orders
    ADD CONSTRAINT orders_processed_by_user_id_fkey
        FOREIGN KEY (processed_by_user_id) REFERENCES users(user_id);

DROP TABLE admin_roles;
DROP TABLE customer_roles;
DROP TABLE admin;