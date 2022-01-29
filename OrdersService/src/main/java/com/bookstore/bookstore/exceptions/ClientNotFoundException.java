package com.bookstore.bookstore.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Could not found the client");
    }
}
