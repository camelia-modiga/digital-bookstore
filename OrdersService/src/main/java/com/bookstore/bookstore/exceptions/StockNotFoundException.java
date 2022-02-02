package com.bookstore.bookstore.exceptions;

public class StockNotFoundException extends RuntimeException{
    public StockNotFoundException(){
        super("Insufficient stock");
    }
}
