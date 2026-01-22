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
    updated_date_time timestamp    NOT NULL
);

-- =============================================
-- product
-- =============================================
CREATE TABLE IF NOT EXISTS product
(
    product_id        uuid PRIMARY KEY,
    name              varchar(100)     NOT NULL,
    description       text             NULL,
    price             double precision NOT NULL,
    picture_url       varchar(512)     NOT NULL,
    balance           integer          NOT NULL, -- you can add DEFAULT 0 if wanted
    category_id       uuid             NULL,     -- nullable as requested
    created_date_time timestamp        NOT NULL,
    updated_date_time timestamp        NOT NULL,


    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
            REFERENCES category (category_id)
            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS supplier
(
    supplier_id       uuid PRIMARY KEY,
    name              varchar(100) NOT NULL,
    email             varchar(255) NOT NULL UNIQUE,
    created_date_time timestamp    NOT NULL,
    updated_date_time timestamp    NOT NULL
);
