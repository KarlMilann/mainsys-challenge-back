INSERT INTO user (id, password, username)
VALUES (14, '$2a$10$c3ZeCRY2GQxqGRGPzlkWvujL6z3UIBeY/uHlwkXpGgMrFI8y.ySRG', 'admin');
INSERT INTO user (id, password, username)
VALUES (15, '$2a$10$GRqcwMngPmNX5a/3V6tbDe2OAEetLmTTY0HwvluoakxQTyPqgQmpe', 'user');
INSERT INTO user (id, password, username)
VALUES (16, '$2a$10$GRqcwMngPmNX5a/3V6tbDe2OAEetLmTTY0HwvluoakxQTyPqgQmpe', 'user2');
INSERT INTO roles (id, name)
VALUES (2, 'ROLE_ADMIN');
INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER');
INSERT INTO user_roles (user_id, role_id)
VALUES (14, 1);
INSERT INTO user_roles (user_id, role_id)
VALUES (15, 1);
INSERT INTO user_roles (user_id, role_id)
VALUES (16, 1);
INSERT INTO user_roles (user_id, role_id)
VALUES (14, 2);
INSERT INTO sales (id, product_name, product_profit, date, user)
