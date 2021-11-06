package com.bookstore.bookstore.services.exceptions;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException() {
        super("Could not find author");
    }
}
