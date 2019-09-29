DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
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

INSERT INTO menus (id, name, price, date, rest_id) VALUES
(100021, 'BigMac', 2000, '2019-08-16', 100010),
(100022, 'CheeseBurger', 1000, '2019-08-16', 100010),
(100023, 'Fries', 1000, '2019-08-16', 100010),
(100024, 'Twister', 1000, '2019-08-16', 100011),
(100025, 'BoxMaster', 1000, '2019-08-16', 100011),
(100026, 'BigMac', 2000, today(), 100010),
(100027, 'BigMac2', 1000, today(), 100010),
(100028, 'Fries', 1000, today(), 100010),
(100029, 'Twister2', 1000, today(), 100011),
(100030, 'BoxMaster2', 1000, today(), 100011);

INSERT INTO votes (id, date, user_id, rest_id) VALUES
(100040, '2019-08-16', 100000, 100010),
(100041, today(), 100000, 100011),
(100042, '2019-08-16', 100001, 100010),
(100043, today(), 100001, 100011);
