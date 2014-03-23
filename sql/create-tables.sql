CREATE TABLE users (id SERIAL PRIMARY KEY, name VARCHAR, pass CHAR(24));
CREATE TABLE memo (id SERIAL PRIMARY KEY, user_id INTEGER REFERENCES users(id), name VARCHAR, content VARCHAR, created TIMESTAMP);
CREATE TABLE task (memo_id INTEGER REFERENCES memo(id), priority INT, closed TIMESTAMP);
CREATE TABLE category (id SERIAL PRIMARY KEY, user_id INTEGER REFERENCES users(id), name VARCHAR, parent_id INTEGER REFERENCES category(id));
CREATE TABLE memo_category (memo_id INTEGER REFERENCES memo(id), category_id INTEGER REFERENCES category(id));
