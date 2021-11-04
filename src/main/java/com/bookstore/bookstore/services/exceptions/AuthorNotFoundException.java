package com.bookstore.bookstore.services.exceptions;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException(Integer id) {
        super("Could not find author with id "+id);
    }
}
