package com.bookstore.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @NotEmpty(message = "Book's ISBN can not be null or empty")
    private String isbn;

    @NotEmpty(message = "Book's title can not be null or empty")
    private String title;

    @NotNull(message = "Book's price can not be null")
    @PositiveOrZero(message = "Book's price can not be negative")
    private Double price;

    @Builder.Default
    @Positive(message = "Book's quantity can not be negative")
    private Integer quantity = 1;
}