INSERT INTO user (id, password, username, total_profit)
VALUES (14, '$2a$10$c3ZeCRY2GQxqGRGPzlkWvujL6z3UIBeY/uHlwkXpGgMrFI8y.ySRG', 'admin', 12000);
INSERT INTO user (id, password, username, total_profit)
VALUES (15, '$2a$10$GRqcwMngPmNX5a/3V6tbDe2OAEetLmTTY0HwvluoakxQTyPqgQmpe', 'user', 30000000+12);
INSERT INTO user (id, password, username, total_profit)
VALUES (16, '$2a$10$GRqcwMngPmNX5a/3V6tbDe2OAEetLmTTY0HwvluoakxQTyPqgQmpe', 'user2', 100000);
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
Insert into sales (id, product_name, product_profit, date, user)
Values (1,'mansion', 12000, '2019-09-24', 14);
Insert into sales (id, product_name, product_profit, date, user)
Values (2, 'rabbit', 12, '2019-05-12', 15);
Insert into sales (id, product_name, product_profit, date, user)
Values (3,'omaewamo shinderu', 100000, '2019-06-17',16);
Insert into sales (id, product_name, product_profit, date, user)
Values (4, '50 shades of grey', 30000000, '2020-03-05',15);