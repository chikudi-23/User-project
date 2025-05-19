// DatabaseManager.java
package lms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db"; // Replace with your DB URL
    private static final String DB_USER = "root"; // Replace with your DB username
    private static final String DB_PASSWORD = "Sneha@12"; // Replace with your DB password

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, copies_available) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getCopiesAvailable());
            pstmt.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), rs.getInt("copies_available"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving book: " + e.getMessage());
        }
        return null;
    }

    public void rentBook(int bookId) {
        String sqlCheckAvailability = "SELECT copies_available FROM books WHERE book_id = ?";
        String sqlUpdateCopies = "UPDATE books SET copies_available = copies_available - 1 WHERE book_id = ?";
        String sqlCreateLoan = "INSERT INTO loans (book_id, issue_date) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheckAvailability);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdateCopies);
             PreparedStatement pstmtLoan = conn.prepareStatement(sqlCreateLoan)) {

            pstmtCheck.setInt(1, bookId);
            ResultSet rs = pstmtCheck.executeQuery();

            if (rs.next() && rs.getInt("copies_available") > 0) {
                pstmtUpdate.setInt(1, bookId);
                pstmtUpdate.executeUpdate();

                pstmtLoan.setInt(1, bookId);
                pstmtLoan.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                pstmtLoan.executeUpdate();
                System.out.println("Book with ID " + bookId + " rented successfully.");
            } else {
                System.out.println("Book with ID " + bookId + " is not available for renting.");
            }

        } catch (SQLException e) {
            System.err.println("Error renting book: " + e.getMessage());
        }
    }

    public void returnBook(int bookId) {
        String sqlCheckActiveLoan = "SELECT loan_id FROM loans WHERE book_id = ? AND return_date IS NULL";
        String sqlUpdateLoan = "UPDATE loans SET return_date = ? WHERE loan_id = ?";
        String sqlUpdateCopies = "UPDATE books SET copies_available = copies_available + 1 WHERE book_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheckActiveLoan);
             PreparedStatement pstmtLoan = conn.prepareStatement(sqlUpdateLoan);
             PreparedStatement pstmtCopies = conn.prepareStatement(sqlUpdateCopies)) {

            pstmtCheck.setInt(1, bookId);
            ResultSet rs = pstmtCheck.executeQuery();

            if (rs.next()) {
                int loanId = rs.getInt("loan_id");

                pstmtLoan.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                pstmtLoan.setInt(2, loanId);
                pstmtLoan.executeUpdate();

                pstmtCopies.setInt(1, bookId);
                pstmtCopies.executeUpdate();

                System.out.println("Book with ID " + bookId + " returned successfully.");
            } else {
                System.out.println("No active rental found for book ID " + bookId + ".");
            }

        } catch (SQLException e) {
            System.err.println("Error returning book: " + e.getMessage());
        }
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE copies_available > 0";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                availableBooks.add(new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"), rs.getInt("copies_available")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving available books: " + e.getMessage());
        }
        return availableBooks;
    }
}