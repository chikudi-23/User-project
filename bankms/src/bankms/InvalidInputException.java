/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankms;

// Custom Exception Class
public class InvalidInputException extends Exception {
    public String message; // Changed to public

    public InvalidInputException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return " Invalid Input:" +message;
    }
}

