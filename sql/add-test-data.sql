INSERT INTO users (username, pass, salt)
VALUES (
    'admin',
    '7fcffb0d69e4c4544eab1a0edf8f4144052a51aedda50dfd438308a73a84ff69', -- 'admin'
    'c4fdeb7410242012abcf7a7c5829a1f87e2ef7bda37817cdd798b849b95ca148'
), (
    'testuser',
    '1e10d1d971bad0b95a54743e345fb4b68c2c004c9debe3a3d633bf18213aa5ab', -- 'testpass'
    '4fbaf8a936e15961c3669af945863b480189d03f499b343647e2d90c46ffb733'
);
INSERT INTO memo (userid, memoname, content, created)
VALUES (
    2,
    'Tee leipä',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed fermentum dignissim eros a congue. Donec accumsan, ante eget tempus lobortis, arcu urna cursus purus, euismod rhoncus orci felis nec juusto.',
    NOW()
), (
    2,
    'Juo kahvia',
    'Lorem ipsum dolor sit kaffe, consectetur adipiscing elit. Sed fermentum dignissim eros a congue. Donec accumsan, ante eget tempus lobortis, arcu urna cursus purus, euismod rhoncus orci felis nec justo.',
    NOW()
);
INSERT INTO memo (userid, memoname, content, created)
VALUES (
    2,
    'Muistio',
    'Tämä on muistio eikä askare',
    NOW()
);
INSERT INTO task (memoid, priority) VALUES (1, 1), (2, 1);
INSERT INTO category (userid, catname) VALUES (2, 'Ruoka'), (2, 'Aamupala');
INSERT INTO category (userid, catname, parentid) VALUES (2, 'Aamuleipä', 1);
INSERT INTO memo_category (memoid, catid) VALUES (1, 2), (1, 3), (2, 1);
