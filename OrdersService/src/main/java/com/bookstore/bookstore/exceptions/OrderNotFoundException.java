package com.bookstore.bookstore.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(){
        super("Could not found the order");
    }
}
