INSERT INTO users (name) VALUES ('testuser');
INSERT INTO memo (user_id, name, content, created) VALUES (1, 'Test memo', 'Lorem Ipsum...', NOW());
INSERT INTO task (memo_id, priority) VALUES (1, 1);
INSERT INTO category (user_id, name) VALUES (1, 'Category A');
INSERT INTO category (user_id, name) VALUES (1, 'Category B');
INSERT INTO category (user_id, name, parent_id) VALUES (1, 'Subcategory 1', 1);
INSERT INTO memo_category (memo_id, category_id) VALUES (1, 2);
INSERT INTO memo_category (memo_id, category_id) VALUES (1, 3);
