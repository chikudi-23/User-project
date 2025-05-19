package lms;


import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private Library library;
    private Scanner scanner;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]+$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");
    private static final String LIBRARY_NAME = "Cartoon Library";
    private static final String LIBRARY_ADDRESS = "Bavdhan, Pune, India";

    public ConsoleUI(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Find Book by ID");
            System.out.println("3. Rent Book");
            System.out.println("4. Return Book");
            System.out.println("5. About Us");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        addBook();
                        break;
                    case "2":
                        findBookById();
                        break;
                    case "3":
                        rentBook();
                        break;
                    case "4":
                        returnBook();
                        break;
                    case "5":
                        displayLibraryInfo();
                        break;
                    case "6":
                        System.out.println("Exiting system.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void displayLibraryInfo() {
        System.out.println("\n--- About Us ---");
        System.out.println("Library Name: " + LIBRARY_NAME);
        System.out.println("Address: " + LIBRARY_ADDRESS);
        System.out.println("--- End of Information ---");
    }

    private void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        if (!isValidName(title)) {
            System.out.println("Invalid title. Only alphabetic characters and spaces are allowed.");
            return;
        }

        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        if (!isValidName(author)) {
            System.out.println("Invalid author name. Only alphabetic characters and spaces are allowed.");
            return;
        }

        int copies = 0;
        System.out.print("Enter number of copies: ");
        String copiesStr = scanner.nextLine();
        if (!isValidNumber(copiesStr)) {
            System.out.println("Invalid number of copies. Please enter a valid integer.");
            return;
        }
        try {
            copies = Integer.parseInt(copiesStr);
            if (copies <= 0) {
            System.out.println("Number of copies must be greater than 0.");
            return;
            }

        } catch (NumberFormatException e) {
            System.err.println("Error parsing number of copies.");
            return;
        }

        library.addBook(title, author, copies);
    }

    private void findBookById() {
        System.out.print("Enter book ID to search: ");
        String bookIdStr = scanner.nextLine();
        if (!isValidNumber(bookIdStr)) {
            System.out.println("Invalid book ID. Please enter a valid integer.");
            return;
        }
        try {
            int bookId = Integer.parseInt(bookIdStr);
            Book book = library.findBookById(bookId);
            if (book != null) {
                System.out.println("Book found: " + book);
            } else {
                System.out.println("Book with ID " + bookId + " not found.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing book ID.");
        }
    }

    private void rentBook() {
        System.out.print("Enter ID of the book to rent: ");
        String bookIdStr = scanner.nextLine();
        if (!isValidNumber(bookIdStr)) {
            System.out.println("Invalid book ID. Please enter a valid integer.");
            return;
        }
        try {
            int bookId = Integer.parseInt(bookIdStr);
            Book book = library.findBookById(bookId);
            if (book != null && book.getCopiesAvailable() > 0) {
                library.rentBook(bookId);
                System.out.println("Book rented: " + book.getTitle() + " by " + book.getAuthor());
            } else if (book == null) {
                System.out.println("Book with ID " + bookId + " not found.");
            } else {
                System.out.println("Book with ID " + bookId + " is not available for renting.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid book ID.");
        }
    }

    private void returnBook() {
        System.out.print("Enter ID of the book being returned: ");
        String bookIdStr = scanner.nextLine();
        if (!isValidNumber(bookIdStr)) {
            System.out.println("Invalid book ID. Please enter a valid integer.");
            return;
        }
        try {
            int bookId = Integer.parseInt(bookIdStr);
            Book book = library.findBookById(bookId);
            if (book != null) {
                library.returnBook(bookId);
                System.out.println("Book returned: " + book.getTitle() + " by " + book.getAuthor());
            } else {
                System.out.println("Book with ID " + bookId + " not found.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid book ID.");
        }
    }

    private boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    private boolean isValidNumber(String number) {
        return number != null && NUMBER_PATTERN.matcher(number).matches();
    }
}