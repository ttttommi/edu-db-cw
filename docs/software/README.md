# Реалізація інформаційного та програмного забезпечення

## SQL-скрипт для створення на початкового наповнення бази даних

```sql
-- Postgresql

-- SCHEMA: public

DROP SCHEMA IF EXISTS public CASCADE;

CREATE SCHEMA IF NOT EXISTS public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT USAGE ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;


-- Table: public.users

-- DROP TABLE IF EXISTS public.users CASCADE;

CREATE TABLE IF NOT EXISTS public.users
(
    id bigserial NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255),
    password character varying(255) NOT NULL,
    second_name character varying(255),
    username character varying(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_username_key UNIQUE (username)
)
    TABLESPACE pg_default;


-- Table: public.roles

-- DROP TABLE IF EXISTS public.roles CASCADE;

CREATE TABLE IF NOT EXISTS public.roles
(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT roles_name_key UNIQUE (name)
)
    TABLESPACE pg_default;


-- Table: public.user_roles

-- DROP TABLE IF EXISTS public.user_roles;

CREATE TABLE IF NOT EXISTS public.user_roles
(
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id)
    REFERENCES public.roles (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
)
    TABLESPACE pg_default;


-- Table: public.permissions

-- DROP TABLE IF EXISTS public.permissions CASCADE;

CREATE TABLE IF NOT EXISTS public.permissions
(
    id bigserial NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT permissions_pkey PRIMARY KEY (id),
    CONSTRAINT permissions_type_key UNIQUE (type)
)
    TABLESPACE pg_default;


-- Table: public.user_permissions

-- DROP TABLE IF EXISTS public.user_permissions;

CREATE TABLE IF NOT EXISTS public.user_permissions
(
    permission_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT user_permissions_pkey PRIMARY KEY (permission_id, user_id),
    CONSTRAINT fk_user_permissions_permission_id FOREIGN KEY (permission_id)
        REFERENCES public.permissions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_user_permissions_user_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)
    TABLESPACE pg_default;


-- Table: public.datafiles

-- DROP TABLE IF EXISTS public.datafiles CASCADE;

CREATE TABLE IF NOT EXISTS public.datafiles
(
    last_update date NOT NULL,
    id bigserial NOT NULL,
    content character varying(255) NOT NULL,
    description character varying(255),
    format character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    CONSTRAINT datafiles_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;


-- Table: public.requests

-- DROP TABLE IF EXISTS public.requests CASCADE;

CREATE TABLE IF NOT EXISTS public.requests
(
    request_date date NOT NULL,
    datafile_id bigint NOT NULL,
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    content character varying(255) NOT NULL,
    description character varying(255),
    format character varying(255) NOT NULL,
    message character varying(255),
    name character varying(255) NOT NULL,
    CONSTRAINT requests_pkey PRIMARY KEY (id),
    CONSTRAINT fk_requests_user_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_requests_datafile_id FOREIGN KEY (datafile_id)
        REFERENCES public.datafiles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;


BEGIN;

-- Додавання першого адміна в таблицю users
-- login : admin / admin@example.com
-- password : unhacked123admin456password
INSERT INTO public.users (email, first_name, password, second_name, username)
VALUES ('admin@example.com', 'Kolya', '$2a$12$F7R/17QfquhPVLiJ5lQPfOXYau8oqbGWxkkw2x865KPM1u/KMqIIe', 'Tishchenko', 'admin');

-- Додавання ролей в таблицю roles
INSERT INTO public.roles (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- Додавання дозволів у таблицю roles
INSERT INTO public.permissions (type)
VALUES ('DOWNLOAD'), ('UPLOAD'), ('EDIT');

-- Видача ролей адміну
INSERT INTO public.user_roles (user_id, role_id)
VALUES (1, 1), (1, 2);

-- Видача дозволів адміну
INSERT INTO public.user_permissions (user_id, permission_id)
VALUES (1, 1), (1, 2), (1, 3);

COMMIT;
```




