CREATE TABLE users (
    userid SERIAL PRIMARY KEY,
    username VARCHAR,
    pass CHAR(64),
    salt CHAR(64)
);
CREATE TABLE memo (
    memoid SERIAL PRIMARY KEY,
    userid INTEGER REFERENCES users(userid),
    memoname VARCHAR,
    content VARCHAR,
    created TIMESTAMP
);
CREATE TABLE task (
    memoid INTEGER REFERENCES memo(memoid),
    priority INT,
    closed TIMESTAMP
);
CREATE TABLE category (
    catid SERIAL PRIMARY KEY,
    userid INTEGER REFERENCES users(userid),
    catname VARCHAR,
    parentid INTEGER REFERENCES category(catid)
);
CREATE TABLE memo_category (
    memoid INTEGER REFERENCES memo(memoid),
    catid INTEGER REFERENCES category(catid)
);
