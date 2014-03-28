INSERT INTO users (username, pass, salt)
VALUES (
    'testuser',
    '1e10d1d971bad0b95a54743e345fb4b68c2c004c9debe3a3d633bf18213aa5ab', -- 'testpass'
    '4fbaf8a936e15961c3669af945863b480189d03f499b343647e2d90c46ffb733'
);
INSERT INTO memo (userid, memoname, content, created)
VALUES (
    1,
    'Test memo',
    'Lorem Ipsum...',
    NOW()
);
INSERT INTO task (memoid, priority) VALUES (1, 1);
INSERT INTO category (userid, catname) VALUES (1, 'Category A');
INSERT INTO category (userid, catname) VALUES (1, 'Category B');
INSERT INTO category (userid, catname, parentid) VALUES (1, 'Subcategory 1', 1);
INSERT INTO memo_category (memoid, catid) VALUES (1, 2);
INSERT INTO memo_category (memoid, catid) VALUES (1, 3);
