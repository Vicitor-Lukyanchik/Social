DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS profiles_groups;
DROP TABLE IF EXISTS profiles_chats;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS chats;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS interests;

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(50)           NOT NULL,
    password VARCHAR(100)          NOT NULL,
    status   VARCHAR(25)           NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE roles
(
    id     BIGSERIAL PRIMARY KEY NOT NULL,
    name   VARCHAR(50),
    status VARCHAR(25)           NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT NOT NULL REFERENCES roles (id)
);

CREATE TABLE profiles
(
    id            BIGSERIAL PRIMARY KEY        NOT NULL,
    firstname     VARCHAR(50)                  NOT NULL,
    lastname      VARCHAR(50)                  NOT NULL,
    email         VARCHAR(100)                 NOT NULL,
    age           INTEGER                      NOT NULL,
    users_id      BIGINT REFERENCES users (id) NOT NULL,
    sex           VARCHAR(15) DEFAULT 'not specified',
    family_status VARCHAR(50) DEFAULT 'not specified',
    town          VARCHAR(50) DEFAULT 'Unknown',
    phone         VARCHAR(15) DEFAULT 'not specified',
    CHECK (sex IN ('not specified', 'MALE', 'FEMALE')),
    CHECK (family_status IN ('not specified', 'married', 'not married', 'in love', 'actively looking'))
);

CREATE TABLE profiles_chats
(
    profiles_id BIGINT NOT NULL REFERENCES profiles (id),
    chats_id    BIGINT NOT NULL REFERENCES chats (id)
);

CREATE TABLE chats
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50)           NOT NULL
);

CREATE TABLE messages
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    chats_id  BIGINT                NOT NULL REFERENCES chats (id),
    text      VARCHAR(255)          NOT NULL,
    date_time TIMESTAMP             NOT NULL
);

CREATE TABLE interests
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50)           NOT NULL
);

CREATE TABLE groups
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    name         VARCHAR(50)           NOT NULL,
    interests_id BIGINT                NOT NULL REFERENCES interests (id)
);

CREATE TABLE posts
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    groups_id BIGINT                NOT NULL REFERENCES groups (id),
    title     VARCHAR(80)           NOT NULL,
    text      VARCHAR(255)          NOT NULL
);

CREATE TABLE profiles_groups
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    profile_id BIGINT                NOT NULL REFERENCES profiles (id),
    group_id   BIGINT                NOT NULL REFERENCES groups (id)
);
