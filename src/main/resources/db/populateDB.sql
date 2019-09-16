DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
DELETE FROM menu_dish;
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
-- 100002
('MacDonalds'),
-- 100003
('KFC');

INSERT INTO menus (date, rest_id) VALUES
-- 100004
('2019-08-16', 100002),
-- 100005
('2019-08-16', 100003),
-- 100006
(today(), 100002),
-- 100007
(today(), 100003);

INSERT INTO dishes (name, price, rest_id) VALUES
-- 100016
('BigMac', 2000, 100002),
-- 100017
('CheeseBurger', 1000, 100002),
-- 100018
('Fries', 1000, 100002),
-- 100019
('BigMac2', 1000, 100002),
-- 100020
('Twister', 1000, 100003),
-- 100021
('BoxMaster', 1000, 100003),
-- 100022
('Twister2', 1000, 100003),
-- 100023
('BoxMaster2', 1000, 100003);

INSERT INTO votes (date, user_id, rest_id) VALUES
-- 100024
('2019-08-16', 100000, 100002),
-- 100025
(today(), 100000, 100003),
-- 100026
('2019-08-16', 100001, 100002),
-- 100027
(today(), 100001, 100002);

INSERT INTO MENU_DISH (menu_id, dish_id) VALUES
(100004, 100016),
(100004, 100017),
(100005, 100020),
(100005, 100021),
(100006, 100017),
(100006, 100018),
(100006, 100019),
(100007, 100022),
(100007, 100023);