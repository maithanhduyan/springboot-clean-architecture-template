-- public.product definition
-- Drop table
-- DROP TABLE public.product;
CREATE TABLE public.product (
    id varchar(255) NOT NULL,
    code varchar(255) NULL,
    created_date timestamp NULL,
    "name" varchar(255) NOT NULL,
    updated_date timestamp NULL,
    CONSTRAINT product_pkey null,
    CONSTRAINT uk_jmivyxk9rmgysrmsqw15lqr5b null
);