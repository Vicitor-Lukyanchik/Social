INSERT INTO role (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users (username, password)
VALUES ('user', '$2a$12$l7R59Z4EzXqzPwKB1wEhU.QMdvp.ITrKlEN5SJkQRaSriVwxbeNlG'),
       ('admin', '$2a$12$ObOzgB2NDmPgrOJAFEitre0vrZnZRkxrnmscmYoUWXozgbGhLFAsC'),
       ('user2', '$2y$10$6M.uvcBY7V9riVQLYnvJlu.Hks/jmLHWdJJH1KYczfMhvcO0JGLTC');

INSERT INTO profile (firstname, lastname, email, age, user_id, sex, family_status, town, phone)
VALUES ('User', 'User', 'user@gmail.com', '18', 1, 'MALE', 'in love', 'Baranovichi', '336344224'),
       ('Admin', 'Admin', 'admin@gmail.com', '20', 2, 'MALE', 'married', 'Minsk', '293218715'),
       ('User2', 'User2', 'user1@gmail.com', '43', 3, 'MALE', 'not married', 'Gomel', '293618715');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 1);