package com.bookstore.bookstore.services.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() {
        super("Could not find book");
    }

}
