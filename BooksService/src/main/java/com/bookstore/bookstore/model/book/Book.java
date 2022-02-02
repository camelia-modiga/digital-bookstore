package com.bookstore.bookstore.model.book;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Table(name = "book", indexes = {
        @Index(name = "genre_idx", columnList = "genre"),
        @Index(name = "year_idx", columnList = "year")
})
@Entity
public class Book {
    @Schema(description = "Unique identifier of the book.", example = "ISBN 978-3-16-148410-0", required = true)
    @Id
    @Column(name = "isbn", nullable = false, length = 50)
    private String isbn;

    @Schema(description = "Title of the book.", example = "Triangle at Rhodes", required = true)
    @Size(max = 50)
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Schema(description = "Name of the publisher.", example = "The Witness", required = true)
    @Size(max = 50)
    @Column(name = "publisher", nullable = false, length = 50)
    private String publisher;

    @Schema(description = "Year of publication.", example = "2010", required = true)
    @Column(name = "year", nullable = false)
    private Integer year;

    @Schema(description = "Genre of the book.", example = "Mister", required = true)
    @Size(max = 50)
    @Column(name = "genre", nullable = false, length = 50)
    private String genre;

    private Double price;
    private Integer stock;

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}