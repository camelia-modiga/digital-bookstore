package com.bookstore.bookstore.model;

import lombok.Builder;

@Builder
public class Book {

    private String isbn;
    private String title;
    private Double price;
    @Builder.Default
    private Integer quantity = 1;

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}