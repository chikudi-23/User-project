
-- Create Database
CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- Create Tables
CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    author VARCHAR(100),
    total_copies INT,
    available_copies INT
);

CREATE TABLE members (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT,
    member_id INT,
    borrow_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);

-- Sample Data
INSERT INTO books (title, author, total_copies, available_copies) VALUES
('The Hobbit', 'J.R.R. Tolkien', 5, 5),
('1984', 'George Orwell', 4, 4),
('Clean Code', 'Robert C. Martin', 3, 3);

INSERT INTO members (name, email) VALUES
('Alice', 'alice@example.com'),
('Bob', 'bob@example.com');
