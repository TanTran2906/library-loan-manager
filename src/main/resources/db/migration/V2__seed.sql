-- Seed data for local development. Ids are explicit so loan FKs are deterministic.

INSERT INTO book (id, title, author, isbn, published_year, total_copies) VALUES
 (1, 'The Pragmatic Programmer', 'Andrew Hunt, David Thomas', '9780135957059', 2019, 3),
 (2, 'Clean Code', 'Robert C. Martin', '9780132350884', 2008, 2),
 (3, 'Designing Data-Intensive Applications', 'Martin Kleppmann', '9781449373320', 2017, 4),
 (4, 'Refactoring', 'Martin Fowler', '9780134757599', 2018, 2),
 (5, 'Domain-Driven Design', 'Eric Evans', '9780321125217', 2003, 1),
 (6, 'Effective Java', 'Joshua Bloch', '9780134685991', 2018, 3),
 (7, 'The Mythical Man-Month', 'Frederick P. Brooks Jr.', '9780201835953', 1995, 1),
 (8, 'Structure and Interpretation of Computer Programs', 'Harold Abelson, Gerald Jay Sussman', '9780262510875', 1996, 2);

INSERT INTO member (id, full_name, email) VALUES
 (1, 'Nguyen Van An', 'an.nguyen@example.com'),
 (2, 'Tran Thi Binh', 'binh.tran@example.com'),
 (3, 'Le Hoang Cuong', 'cuong.le@example.com'),
 (4, 'Pham Thu Dung', 'dung.pham@example.com');

INSERT INTO loan (id, book_id, member_id, borrowed_at, due_at, returned_at) VALUES
 (1, 1, 1, '2026-06-01', '2026-06-15', '2026-06-12'),  -- returned
 (2, 3, 2, '2026-06-20', '2026-07-04', NULL),           -- overdue, still out
 (3, 2, 3, '2026-07-01', '2026-07-15', NULL),           -- active
 (4, 6, 1, '2026-05-10', '2026-05-24', '2026-05-20'),   -- returned
 (5, 4, 4, '2026-07-05', '2026-07-19', NULL),           -- active
 (6, 5, 2, '2026-06-25', '2026-07-09', NULL);           -- overdue by one day
