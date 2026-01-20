DROP TABLE IF Exists [user]
DROP TABLE IF Exists product
DROP TABLE IF Exists category
-- schema.sql
-- Column names follow Spring Boot's default: camelCase → snake_case + lowercase
-- Table names are lowercase entity names

-- =============================================
-- user (instead of [User])
-- =============================================
CREATE TABLE [user] (
    id                UNIQUEIDENTIFIER    PRIMARY KEY,
    first_name        NVARCHAR(100)       NOT NULL,
    last_name         NVARCHAR(100)       NOT NULL,
    role              NVARCHAR(50)        NOT NULL,      -- stored as string
    email             NVARCHAR(255)       NOT NULL UNIQUE,
    password          NVARCHAR(255)       NOT NULL,
    is_enabled        BIT                 NOT NULL,
    created_date_time DATETIME2           NOT NULL,
    updated_date_time DATETIME2           NOT NULL
    );


-- =============================================
-- category
-- =============================================
CREATE TABLE category (
                          id    UNIQUEIDENTIFIER    PRIMARY KEY,
                          name  NVARCHAR(100)       NOT NULL UNIQUE
);


-- =============================================
-- product
-- =============================================
CREATE TABLE product (
                         id            UNIQUEIDENTIFIER    PRIMARY KEY,
                         name          NVARCHAR(100)       NOT NULL,
                         description   NVARCHAR(MAX)       NULL,
                         price         FLOAT               NOT NULL,
                         picture_url   NVARCHAR(512)       NOT NULL,
                         balance       INT                 NOT NULL,     -- DEFAULT 0 optional
                         category_id   UNIQUEIDENTIFIER    NULL,         -- nullable as requested

                         CONSTRAINT FK_product_category
                             FOREIGN KEY (category_id)
                                 REFERENCES category(id)
                                 ON DELETE SET NULL
);
