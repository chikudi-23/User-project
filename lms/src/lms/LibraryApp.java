package lms;

public class LibraryApp {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        Library library = new Library(dbManager);
        ConsoleUI ui = new ConsoleUI(library);
        ui.run();
    }
}