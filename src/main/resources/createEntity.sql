ALTER TABLE user auto_increment = 1;
ALTER TABLE orders auto_increment = 1;
INSERT INTO user (nickname, password) VALUES('hello', '121');
INSERT INTO user (nickname, password) VALUES('hello1', '123');
INSERT INTO user (nickname, password) VALUES('hello2', '124');
INSERT INTO user (nickname, password) VALUES('hello3', '125');
INSERT INTO user (nickname, password) VALUES('hello4', '126');
INSERT INTO orders (item, user_user_id) VALUES ('hel1lo', 1);
INSERT INTO orders (item, user_user_id) VALUES('korea', 3);

