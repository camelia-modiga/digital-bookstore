package com.bookstore.bookstore.model.book;

import javax.persistence.*;

@Table(name = "book", indexes = {
        @Index(name = "genre_idx", columnList = "genre"),
        @Index(name = "year_idx", columnList = "year")
})
@Entity
public class Book {
    @Id
    @Column(name = "isbn", nullable = false, length = 50)
    private String isbn;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "publisher", nullable = false, length = 50)
    private String publisher;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "genre", nullable = false, length = 50)
    private String genre;

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

    public void setIsbn(String id) {
        this.isbn = isbn;
    }
}