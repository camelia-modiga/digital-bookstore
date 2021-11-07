package com.bookstore.bookstore.exceptions;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException() {
        super("Could not find author");
    }
}
