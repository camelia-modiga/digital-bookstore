package com.bookstore.bookstore.model.bookauthor;

import com.bookstore.bookstore.model.author.Author;

import javax.persistence.*;

@Table(name = "book_author")
@Entity
public class BookAuthor {
    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BookAuthorId getId() {
        return id;
    }

    public void setId(BookAuthorId id) {
        this.id = id;
    }
}