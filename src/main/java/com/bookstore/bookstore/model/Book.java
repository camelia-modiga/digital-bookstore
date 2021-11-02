package com.bookstore.bookstore.model;

import javax.persistence.*;

@Table(name = "book", indexes = {
        @Index(name = "idx_book_genre", columnList = "genre"),
        @Index(name = "idx_book_year", columnList = "year"),
        @Index(name = "title_UNIQUE", columnList = "title", unique = true)
})
@Entity
public class Book {
    @Id
    @Column(name = "isbn", nullable = false, length = 50)
    private String id;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @Column(name = "publisher", nullable = false, length = 45)
    private String publisher;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "genre", nullable = false, length = 45)
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}