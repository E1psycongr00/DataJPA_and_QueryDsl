ALTER TABLE user auto_increment = 1;
ALTER TABLE orders auto_increment = 1;
INSERT INTO user (nickname, password, age) VALUES('hello', '121', 10);
INSERT INTO user (nickname, password, age) VALUES('hello1', '123', 20);
INSERT INTO user (nickname, password, age) VALUES('hello2', '124', 10);
INSERT INTO user (nickname, password, age) VALUES('hello3', '125', 30);
INSERT INTO user (nickname, password, age) VALUES('hello4', '126', 11);
INSERT INTO user (nickname, password, age) VALUES('mina', '126', 22);
INSERT INTO user (nickname, password, age) VALUES('alex', '126', 25);
INSERT INTO user (nickname, password, age) VALUES('aladin', '126', 26);

INSERT INTO orders (item, user_user_id) VALUES ('rest', 1);
INSERT INTO orders (item, user_user_id) VALUES ('rest', 2);
INSERT INTO orders (item, user_user_id) VALUES ('rest', 3);
INSERT INTO orders (item, user_user_id) VALUES ('spring', 1);
INSERT INTO orders (item, user_user_id) VALUES ('food', 1);
INSERT INTO orders (item, user_user_id) VALUES ('company', 2);
INSERT INTO orders (item, user_user_id) VALUES ('study', 2);
INSERT INTO orders (item, user_user_id) VALUES('korea', 3);


INSERT INTO player (name, age, sports_category)
VALUES ('mina', 22, 'football');
INSERT INTO player (name, age, sports_category)
VALUES ('handerson', 27, 'football');
INSERT INTO player (name, age, sports_category)
VALUES ('son', 28, 'football');
INSERT INTO player (name, age, sports_category)
VALUES ('joden', 30, 'basketball');