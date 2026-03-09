-- Countries
INSERT INTO country (id, name, continent) VALUES (1, 'United States', 'North America');
INSERT INTO country (id, name, continent) VALUES (2, 'United Kingdom', 'Europe');
INSERT INTO country (id, name, continent) VALUES (3, 'France', 'Europe');
INSERT INTO country (id, name, continent) VALUES (4, 'Germany', 'Europe');
INSERT INTO country (id, name, continent) VALUES (5, 'Japan', 'Asia');

-- Authors
INSERT INTO author (id, name, surname, country_id) VALUES (1, 'Stephen', 'King', 1);
INSERT INTO author (id, name, surname, country_id) VALUES (2, 'J.K.', 'Rowling', 2);
INSERT INTO author (id, name, surname, country_id) VALUES (3, 'Victor', 'Hugo', 3);
INSERT INTO author (id, name, surname, country_id) VALUES (4, 'Franz', 'Kafka', 4);
INSERT INTO author (id, name, surname, country_id) VALUES (5, 'Haruki', 'Murakami', 5);

-- Books
INSERT INTO book (id, name, category, author_id, state, available_copies) VALUES (1, 'It', 'THRILLER', 1, 'GOOD', 5);
INSERT INTO book (id, name, category, author_id, state, available_copies) VALUES (2, 'The Shining', 'THRILLER', 1, 'GOOD', 3);
INSERT INTO book (id, name, category, author_id, state, available_copies) VALUES (3, 'Harry Potter and the Philosophers Stone', 'FANTASY', 2, 'GOOD', 10);
INSERT INTO book (id, name, category, author_id, state, available_copies) VALUES (4, 'Les Miserables', 'CLASSICS', 3, 'BAD', 2);
INSERT INTO book (id, name, category, author_id, state, available_copies) VALUES (5, 'The Trial', 'DRAMA', 4, 'GOOD', 4);
INSERT INTO book (id, name, category, author_id, state, available_copies) VALUES (6, 'Norwegian Wood', 'NOVEL', 5, 'GOOD', 7);
INSERT INTO book (id, name, category, author_id, state, available_copies) VALUES (7, 'The Hunchback of Notre-Dame', 'HISTORY', 3, 'BAD', 1);
INSERT INTO book (id, name, category, author_id, state, available_copies) VALUES (8, 'Kafka on the Shore', 'FANTASY', 5, 'GOOD', 6);

-- Reset sequences
SELECT setval('country_id_seq', (SELECT MAX(id) FROM country));
SELECT setval('author_id_seq', (SELECT MAX(id) FROM author));
SELECT setval('book_id_seq', (SELECT MAX(id) FROM book));

