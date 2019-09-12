DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO  restaurants (name) VALUES
('MacDonalds'),
('KFC');

INSERT INTO menus (date, rest_id) VALUES
('2019-08-16', 100002),
('2019-08-16', 100003),
('2019-08-17', 100002),
('2019-08-17', 100003);

INSERT INTO dishes (name, price, menu_id) VALUES
('BigMac', 2000, 100004),
('CheeseBurger', 1000, 100004),
('Twister', 1000, 100005),
('BoxMaster', 1000, 100005),
('Fries', 1000, 100006),
('BigMac2', 1000, 100006),
('Twister2', 1000, 100007),
('BoxMaster', 1000, 100007);

INSERT INTO votes (date_time, user_id, rest_id) VALUES
('2019-08-16 10:00:00', 100000, 100002),
('2019-08-17 10:00:00', 100000, 100003),
('2019-08-16 10:00:00', 100001, 100002),
('2019-08-17 10:00:00', 100001, 100002);