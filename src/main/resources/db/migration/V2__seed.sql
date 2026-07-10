-- Session 1 practice seed. Every date is a hard literal on purpose (no CURDATE()),
-- so the completion-check counts are reproducible.
--
-- Shape enforced here:
--   book   : 25 rows, exactly ONE (id 25) never appears in loan
--   member : 8 rows
--   loan   : 30 rows spread over exactly 24 distinct books (ids 1..24)
--     - 3  overdue        : due_at '2026-04-15', returned_at NULL
--     - 4  on loan        : due_at '2027-06-30', returned_at NULL
--     - 23 returned       : past due_at, returned_at set

INSERT INTO book (id, title, author, isbn, published_year, total_copies) VALUES
 (1,  'The Pragmatic Programmer', 'Andrew Hunt, David Thomas', '9780135957059', 2019, 3),
 (2,  'Clean Code', 'Robert C. Martin', '9780132350884', 2008, 2),
 (3,  'Designing Data-Intensive Applications', 'Martin Kleppmann', '9781449373320', 2017, 4),
 (4,  'Refactoring', 'Martin Fowler', '9780134757599', 2018, 2),
 (5,  'Domain-Driven Design', 'Eric Evans', '9780321125217', 2003, 1),
 (6,  'Effective Java', 'Joshua Bloch', '9780134685991', 2018, 3),
 (7,  'The Mythical Man-Month', 'Frederick P. Brooks Jr.', '9780201835953', 1995, 1),
 (8,  'Structure and Interpretation of Computer Programs', 'Harold Abelson, Gerald Jay Sussman', '9780262510875', 1996, 2),
 (9,  'Introduction to Algorithms', 'Cormen, Leiserson, Rivest, Stein', '9780262033848', 2009, 2),
 (10, 'The Art of Computer Programming, Vol. 1', 'Donald E. Knuth', '9780201896831', 1997, 1),
 (11, 'Code Complete', 'Steve McConnell', '9780735619678', 2004, 2),
 (12, 'Working Effectively with Legacy Code', 'Michael Feathers', '9780131177055', 2004, 2),
 (13, 'Patterns of Enterprise Application Architecture', 'Martin Fowler', '9780321127426', 2002, 1),
 (14, 'Release It!', 'Michael T. Nygard', '9781680502398', 2018, 2),
 (15, 'Continuous Delivery', 'Jez Humble, David Farley', '9780321601919', 2010, 2),
 (16, 'The DevOps Handbook', 'Gene Kim, Jez Humble, Patrick Debois, John Willis', '9781942788003', 2016, 3),
 (17, 'Accelerate', 'Nicole Forsgren, Jez Humble, Gene Kim', '9781942788331', 2018, 2),
 (18, 'Site Reliability Engineering', 'Betsy Beyer, Chris Jones, Jennifer Petoff, Niall Murphy', '9781491929124', 2016, 2),
 (19, 'Building Microservices', 'Sam Newman', '9781492034025', 2021, 3),
 (20, 'Fundamentals of Software Architecture', 'Mark Richards, Neal Ford', '9781492043454', 2020, 2),
 (21, 'Software Engineering at Google', 'Titus Winters, Tom Manshreck, Hyrum Wright', '9781492082798', 2020, 1),
 (22, 'The Phoenix Project', 'Gene Kim, Kevin Behr, George Spafford', '9781942788294', 2018, 2),
 (23, 'Test-Driven Development by Example', 'Kent Beck', '9780321146533', 2002, 2),
 (24, 'Head First Design Patterns', 'Eric Freeman, Elisabeth Robson', '9781492078005', 2020, 3),
 (25, 'Java Concurrency in Practice', 'Brian Goetz', '9780321349606', 2006, 1);  -- id 25: intentionally never loaned

INSERT INTO member (id, full_name, email) VALUES
 (1, 'Nguyen Van An',   'an.nguyen@example.com'),
 (2, 'Tran Thi Binh',   'binh.tran@example.com'),
 (3, 'Le Hoang Cuong',  'cuong.le@example.com'),
 (4, 'Pham Thu Dung',   'dung.pham@example.com'),
 (5, 'Hoang Minh Duc',  'duc.hoang@example.com'),
 (6, 'Vu Thi Hoa',      'hoa.vu@example.com'),
 (7, 'Dang Van Khoa',   'khoa.dang@example.com'),
 (8, 'Bui Thi Lan',     'lan.bui@example.com');

-- Group A: 3 OVERDUE loans. due_at is in the past and returned_at IS NULL,
-- so they satisfy (returned_at IS NULL AND due_at < CURDATE()).
INSERT INTO loan (id, book_id, member_id, borrowed_at, due_at, returned_at) VALUES
 (1, 1, 1, '2026-04-01', '2026-04-15', NULL),
 (2, 2, 2, '2026-03-25', '2026-04-15', NULL),
 (3, 3, 3, '2026-04-03', '2026-04-15', NULL);

-- Group B: 4 CURRENTLY-ON-LOAN. due_at is a deliberately far-future literal
-- ('2027-06-30') so these count as "on loan", NOT overdue, without using CURDATE().
-- This is a fixed fixture value, not a realistic 14-month loan period.
INSERT INTO loan (id, book_id, member_id, borrowed_at, due_at, returned_at) VALUES
 (4, 4, 4, '2026-06-20', '2027-06-30', NULL),
 (5, 5, 5, '2026-06-28', '2027-06-30', NULL),
 (6, 6, 6, '2026-07-01', '2027-06-30', NULL),
 (7, 7, 7, '2026-07-05', '2027-06-30', NULL);

-- Group C: 23 RETURNED loans (returned_at set). Books 8..24 get their only loan
-- here; books 1..6 get a second, already-returned loan to reach 30 rows while
-- keeping the distinct-book count at 24 (book 25 stays unloaned).
INSERT INTO loan (id, book_id, member_id, borrowed_at, due_at, returned_at) VALUES
 (8,  8,  8, '2025-09-01', '2025-09-15', '2025-09-14'),
 (9,  9,  1, '2025-09-05', '2025-09-19', '2025-09-18'),
 (10, 10, 2, '2025-09-10', '2025-09-24', '2025-09-20'),
 (11, 11, 3, '2025-09-15', '2025-09-29', '2025-09-28'),
 (12, 12, 4, '2025-10-01', '2025-10-15', '2025-10-13'),
 (13, 13, 5, '2025-10-05', '2025-10-19', '2025-10-18'),
 (14, 14, 6, '2025-10-10', '2025-10-24', '2025-10-22'),
 (15, 15, 7, '2025-10-15', '2025-10-29', '2025-10-27'),
 (16, 16, 8, '2025-11-01', '2025-11-15', '2025-11-14'),
 (17, 17, 1, '2025-11-05', '2025-11-19', '2025-11-17'),
 (18, 18, 2, '2025-11-10', '2025-11-24', '2025-11-23'),
 (19, 19, 3, '2025-11-15', '2025-11-29', '2025-11-28'),
 (20, 20, 4, '2025-12-01', '2025-12-15', '2025-12-14'),
 (21, 21, 5, '2025-12-05', '2025-12-19', '2025-12-18'),
 (22, 22, 6, '2025-12-10', '2025-12-24', '2025-12-22'),
 (23, 23, 7, '2025-12-15', '2025-12-29', '2025-12-27'),
 (24, 24, 8, '2026-01-05', '2026-01-19', '2026-01-18'),
 (25, 1,  1, '2026-01-10', '2026-01-24', '2026-01-23'),
 (26, 2,  2, '2026-01-15', '2026-01-29', '2026-01-28'),
 (27, 3,  3, '2026-02-01', '2026-02-15', '2026-02-14'),
 (28, 4,  4, '2026-02-05', '2026-02-19', '2026-02-18'),
 (29, 5,  5, '2026-02-10', '2026-02-24', '2026-02-23'),
 (30, 6,  6, '2026-02-15', '2026-03-01', '2026-02-28');
