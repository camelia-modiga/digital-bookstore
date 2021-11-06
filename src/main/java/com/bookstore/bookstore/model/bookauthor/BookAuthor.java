package com.bookstore.bookstore.model.bookauthor;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.book.Book;

import javax.persistence.*;

@Table(name = "book_author")
@Entity
public class BookAuthor {
    @Id
    @Column(name = "`index`", nullable = false)
    private Integer index;

    @ManyToOne(optional = false)
    @JoinColumn(name = "isbn", nullable = false)
    private Book isbn;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getIsbn() {
        return isbn;
    }

    public void setIsbn(Book isbn) {
        this.isbn = isbn;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer id) {
        this.index = index;
    }
}