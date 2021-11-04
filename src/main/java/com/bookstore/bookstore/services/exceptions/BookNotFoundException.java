package com.bookstore.bookstore.services.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String isbn) {
        super("Could nou find book with isbn "+isbn);
    }
}
