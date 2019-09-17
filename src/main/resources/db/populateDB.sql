DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
DELETE FROM menu_dish;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100100;

INSERT INTO users (id, name, email, password) VALUES
(100000, 'User', 'user@yandex.ru', 'password'),
(100001, 'Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO  restaurants (id, name) VALUES
(100010, 'MacDonalds'),
(100011, 'KFC');

INSERT INTO menus (id, date, rest_id) VALUES
(100021, '2019-08-16', 100010),
(100022, '2019-08-16', 100011),
(100023, today(), 100010),
(100024, today(), 100011);

INSERT INTO dishes (id, name, price, rest_id) VALUES
(100030, 'BigMac', 2000, 100010),
(100031, 'CheeseBurger', 1000, 100010),
(100032, 'Fries', 1000, 100010),
(100033, 'BigMac2', 1000, 100010),
(100034, 'Twister', 1000, 100011),
(100035, 'BoxMaster', 1000, 100011),
(100036, 'Twister2', 1000, 100011),
(100037, 'BoxMaster2', 1000, 100011);

INSERT INTO votes (id, date, user_id, rest_id) VALUES
(100040, '2019-08-16', 100000, 100010),
(100041, today(), 100000, 100011),
(100042, '2019-08-16', 100001, 100010),
(100043, today(), 100001, 100011);

INSERT INTO MENU_DISH (menu_id, dish_id) VALUES
(100021, 100030),
(100021, 100031),
(100022, 100034),
(100022, 100035),
(100023, 100030),
(100023, 100032),
(100023, 100033),
(100024, 100036),
(100024, 100037);