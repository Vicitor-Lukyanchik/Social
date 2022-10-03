DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS interests;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS profile_groups;

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

CREATE TABLE user_roles
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
    user_id       BIGINT REFERENCES users (id) NOT NULL,
    sex           VARCHAR(15) DEFAULT 'not specified',
    family_status VARCHAR(50) DEFAULT 'not specified',
    town          VARCHAR(50) DEFAULT 'Unknown',
    phone         VARCHAR(15) DEFAULT 'not specified',
    CHECK (sex IN ('not specified', 'MALE', 'FEMALE')),
    CHECK (family_status IN ('not specified', 'married', 'not married', 'in love', 'actively looking'))
);

CREATE TABLE friends
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    sender_id   BIGINT                NOT NULL REFERENCES profiles (id),
    receiver_id BIGINT                NOT NULL REFERENCES profiles (id)
);

CREATE TABLE messages
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    friends_id BIGINT                NOT NULL REFERENCES friends (id),
    text       VARCHAR(255)          NOT NULL,
    date_time  TIMESTAMP             NOT NULL
);

CREATE TABLE interests
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50)           NOT NULL
);

CREATE TABLE groups
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(50)           NOT NULL,
    interest_id BIGINT                NOT NULL REFERENCES interests (id)
);

CREATE TABLE posts
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    group_id BIGINT                NOT NULL REFERENCES groups (id),
    title    VARCHAR(80)           NOT NULL,
    text     VARCHAR(255)          NOT NULL
);

CREATE TABLE profile_groups
(
    profile_id BIGINT NOT NULL REFERENCES profiles (id),
    group_id   BIGINT NOT NULL REFERENCES groups (id)
);
