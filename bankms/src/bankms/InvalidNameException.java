/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankms;


public class InvalidNameException extends Exception {
     public String message; // Changed to public

    public InvalidNameException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return " Invalid Name:" +message;
    }
}
