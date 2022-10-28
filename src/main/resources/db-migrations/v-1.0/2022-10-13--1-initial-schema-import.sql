DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS profile_group CASCADE;
DROP TABLE IF EXISTS profile_chat CASCADE;
DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS chat CASCADE;
DROP TABLE IF EXISTS post CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS profile CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS interest CASCADE;


CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(50)           NOT NULL,
    password VARCHAR(60)           NOT NULL,
    status   VARCHAR(25)           NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE role
(
    id     BIGSERIAL PRIMARY KEY NOT NULL,
    name   VARCHAR(50),
    status VARCHAR(25)           NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE user_role
(
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT NOT NULL REFERENCES role (id)
);

CREATE TABLE profile
(
    id            BIGSERIAL PRIMARY KEY        NOT NULL,
    firstname     VARCHAR(50)                  NOT NULL,
    lastname      VARCHAR(50)                  NOT NULL,
    email         VARCHAR(100)                 NOT NULL,
    age           INTEGER                      NOT NULL,
    user_id       BIGINT REFERENCES users (id) NOT NULL,
    sex           VARCHAR(10)  DEFAULT 'UNDEFINED',
    family_status VARCHAR(50) DEFAULT 'not specified',
    town          VARCHAR(50) DEFAULT 'not specified',
    phone         VARCHAR(15) DEFAULT 'not specified',
    CHECK (sex IN ('-', 'MALE', 'FEMALE')),
    CHECK (family_status IN ('not specified', 'married', 'not married', 'in love', 'actively looking'))
);

CREATE TABLE chat
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50)           NOT NULL
);

CREATE TABLE profile_chat
(
    profiles_id BIGINT NOT NULL REFERENCES profile (id),
    chat_id     BIGINT NOT NULL REFERENCES chat (id)
);

CREATE TABLE message
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    chat_id    BIGINT                NOT NULL REFERENCES chat (id),
    profile_id BIGINT                NOT NULL REFERENCES profile (id),
    text       VARCHAR(255)          NOT NULL,
    date_time  TIMESTAMP             NOT NULL
);

CREATE TABLE interest
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50)           NOT NULL
);

CREATE TABLE groups
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(50)           NOT NULL,
    interest_id BIGINT                NOT NULL REFERENCES interest (id),
    profile_id  BIGINT                NOT NULL REFERENCES profile (id)
);

CREATE TABLE post
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    group_id  BIGINT                NOT NULL REFERENCES groups (id),
    chat_id   BIGINT                NOT NULL REFERENCES chat (id),
    title     VARCHAR(50)           NOT NULL,
    text      VARCHAR(255)          NOT NULL,
    date_time TIMESTAMP             NOT NULL
);

CREATE TABLE profile_group
(
    profile_id BIGINT NOT NULL REFERENCES profile (id),
    group_id   BIGINT NOT NULL REFERENCES groups (id)
);
