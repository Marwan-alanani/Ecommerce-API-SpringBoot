-- schema.sql for PostgreSQL
-- (run this via spring.sql.init or flyway/liquibase)

-- =============================================
-- "user" table (quoted because "user" is a reserved word)
-- =============================================
CREATE TABLE IF NOT EXISTS "user"
(
    user_id           uuid PRIMARY KEY,
    first_name        varchar(100) NOT NULL,
    last_name         varchar(100) NOT NULL,
    role              varchar(50)  NOT NULL, -- stored as string ('ADMIN', 'USER', etc.)
    email             varchar(255) NOT NULL UNIQUE,
    password          varchar(255) NOT NULL, -- usually longer for hashes
    is_enabled        boolean      NOT NULL,
    created_date_time timestamptz  NOT NULL,
    updated_date_time timestamptz  NOT NULL
);

-- =============================================
-- category
-- =============================================
CREATE TABLE IF NOT EXISTS category
(
    category_id       uuid PRIMARY KEY,
    name              varchar(100) NOT NULL UNIQUE,
    created_date_time timestamptz  NOT NULL,
    updated_date_time timestamptz  NOT NULL,
    is_enabled        boolean      NOT NULL
);

-- =============================================
-- product
-- =============================================
CREATE TABLE IF NOT EXISTS product
(
    product_id              uuid PRIMARY KEY,
    name                    varchar(100)   NOT NULL,
    description             text           NULL,
    price                   DECIMAL(19, 4) NOT NULL,
    picture_url             varchar(512)   NOT NULL,
    balance                 integer        NOT NULL, -- you can add DEFAULT 0 if wanted
    category_id             uuid           NULL,     -- nullable as requested
    created_date_time       timestamptz    NOT NULL,
    updated_date_time       timestamptz    NOT NULL,
    is_enabled              boolean        NOT NULL,
    total_purchase_price    DECIMAL(19, 4) NOT NULL,
    total_purchase_quantity BIGINT         NOT NULL,


    CONSTRAINT fk_product_category FOREIGN KEY (category_id)
        REFERENCES category (category_id)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS supplier
(
    supplier_id       uuid PRIMARY KEY,
    name              varchar(100) NOT NULL,
    email             varchar(255) NOT NULL UNIQUE,
    created_date_time timestamptz  NOT NULL,
    updated_date_time timestamptz  NOT NULL,
    is_enabled        boolean      NOT NULL
);

CREATE TABLE IF NOT EXISTS purchase
(
    purchase_id       uuid PRIMARY KEY,
    product_id        uuid           NULL,
    unit_price        DECIMAL(19, 4) NOT NULL,
    quantity          integer        NOT NULL,
    supplier_id       uuid           NULL,
    created_date_time timestamptz    NOT NULL,

    CONSTRAINT fk_purchase_product
        FOREIGN KEY (product_id)
            REFERENCES product (product_id)
            ON DELETE SET NULL,

    CONSTRAINT fk_purchase_supplier
        FOREIGN KEY (supplier_id)
            REFERENCES supplier (supplier_id)
            ON DELETE SET NULL

);

CREATE TABLE IF NOT EXISTS carts
(
    cart_id           UUID PRIMARY KEY,

    user_id           UUID        NOT NULL,
    created_date_time timestamptz NOT NULL,
    updated_date_time timestamptz NOT NULL,

    -- Optional: if you want to enforce foreign key to users table
    CONSTRAINT fk_carts_user
        FOREIGN KEY (user_id)
            REFERENCES "user" (user_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS cart_items
(
    cart_item_id      UUID PRIMARY KEY,
    cart_id           UUID    NOT NULL,
    product_id        UUID    NOT NULL,
    quantity          INTEGER NOT NULL,

    -- timestamptzs (even if not mapped in entity – very useful)
    created_date_time timestamptz,
    updated_date_time timestamptz,

    CONSTRAINT fk_cart_items_cart
        FOREIGN KEY (cart_id)
            REFERENCES carts (cart_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

    CONSTRAINT fk_cart_items_product
        FOREIGN KEY (product_id)
            REFERENCES product (product_id)
            ON DELETE RESTRICT
);

Create TABLE IF NOT EXISTS orders
(
    order_id          UUID PRIMARY KEY,
    user_id           UUID           NOT NULL,
    total_price       DECIMAL(19, 4) NOT NULL,
    created_date_time timestamptz    NOT NULL,
    order_status      VARCHAR(50)    NOT NULL,
    CONSTRAINT fk_orders_users
        FOREIGN KEY (user_id)
            REFERENCES "user" (user_id)
            ON DELETE RESTRICT
);

Create TABLE IF NOT EXISTS order_items
(
    order_item_id       UUID PRIMARY KEY,
    order_id            UUID           NOT NULL,
    product_name        VARCHAR(100)   NOT NULL,
    product_picture_url VARCHAR(512)   NOT NULL,
    unit_price          DECIMAL(19, 4) NOT NULL,
    quantity            INTEGER        NOT NULL,
    created_date_time   timestamptz    NOT NULL,
    CONSTRAINT fk_order_items_orders
        FOREIGN KEY (order_id)
            REFERENCES orders (order_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

