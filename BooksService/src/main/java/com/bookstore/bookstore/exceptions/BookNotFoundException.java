package com.bookstore.bookstore.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() {
        super("Could not find book");
    }

}
