package lms;


public class Loan {
    private int loanId;
    private int bookId;
    private String issueDate;
    private String returnDate;

    public Loan(int loanId, int bookId, String issueDate, String returnDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan ID: " + loanId + ", Book ID: " + bookId + ", Issued: " + issueDate + ", Returned: " + returnDate;
    }
}