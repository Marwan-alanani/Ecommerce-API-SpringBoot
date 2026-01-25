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
    created_date_time timestamp    NOT NULL,
    updated_date_time timestamp    NOT NULL
);

-- =============================================
-- category
-- =============================================
CREATE TABLE IF NOT EXISTS category
(
    category_id       uuid PRIMARY KEY,
    name              varchar(100) NOT NULL UNIQUE,
    created_date_time timestamp    NOT NULL,
    updated_date_time timestamp    NOT NULL,
    is_enabled        boolean      NOT NULL
);

-- =============================================
-- product
-- =============================================
CREATE TABLE IF NOT EXISTS product
(
    product_id              uuid PRIMARY KEY,
    name                    varchar(100)     NOT NULL,
    description             text             NULL,
    price                   double precision NOT NULL,
    picture_url             varchar(512)     NOT NULL,
    balance                 integer          NOT NULL, -- you can add DEFAULT 0 if wanted
    category_id             uuid             NULL,     -- nullable as requested
    created_date_time       timestamp        NOT NULL,
    updated_date_time       timestamp        NOT NULL,
    is_enabled              boolean          NOT NULL,
    total_purchase_cost     DECIMAL(19, 4)   NOT NULL,
    total_purchase_quantity BIGINT           NOT NULL,


    CONSTRAINT fk_product_category FOREIGN KEY (category_id)
        REFERENCES category (category_id)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS supplier
(
    supplier_id       uuid PRIMARY KEY,
    name              varchar(100) NOT NULL,
    email             varchar(255) NOT NULL UNIQUE,
    created_date_time timestamp    NOT NULL,
    updated_date_time timestamp    NOT NULL,
    is_enabled        boolean      NOT NULL
);

CREATE TABLE IF NOT EXISTS purchase
(
    purchase_id       uuid PRIMARY KEY,
    product_id        uuid             NULL,
    price             double precision NOT NULL,
    quantity          integer          NOT NULL,
    supplier_id       uuid             NULL,
    created_date_time timestamp        NOT NULL,

    CONSTRAINT fk_purchase_product
        FOREIGN KEY (product_id)
            REFERENCES product (product_id)
            ON DELETE SET NULL,

    CONSTRAINT fk_purchase_supplier
        FOREIGN KEY (supplier_id)
            REFERENCES supplier (supplier_id)
            ON DELETE SET NULL

);

-- Table: baskets
-- ------------------------------------------------------------------------
CREATE TABLE baskets
(
    basket_id         UUID PRIMARY KEY,

    user_id           UUID                     NOT NULL,

    status            VARCHAR(50)              NOT NULL,

    created_date_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Optional: if you want to enforce foreign key to users table
    CONSTRAINT fk_baskets_user
        FOREIGN KEY (user_id)
            REFERENCES "user" (user_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);

-- ------------------------------------------------------------------------
-- Table: basket_items
-- ------------------------------------------------------------------------
CREATE TABLE basket_items
(
    basket_item_id UUID PRIMARY KEY,

    basket_id      UUID           NOT NULL,
    product_id     UUID           NOT NULL,

    quantity       INTEGER        NOT NULL
        CHECK (quantity > 0),

    -- Very strongly recommended: store the price snapshot at time of addition
    -- (product price can change later – you almost always want historical price)
    price          DECIMAL(10, 2) NOT NULL,

    -- Timestamps (even if not mapped in entity – very useful)
    created_at     TIMESTAMP WITH TIME ZONE,
    updated_at     TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_basket_items_basket
        FOREIGN KEY (basket_id)
            REFERENCES baskets (basket_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

    CONSTRAINT fk_basket_items_product
        FOREIGN KEY (product_id)
            REFERENCES product (product_id)
            ON DELETE RESTRICT
);