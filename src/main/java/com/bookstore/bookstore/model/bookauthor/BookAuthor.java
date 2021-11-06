package com.bookstore.bookstore.model.bookauthor;

import com.bookstore.bookstore.model.book.Book;

import javax.persistence.*;

@Entity
public class BookAuthor {

    private @Id Integer index;
    private String isbn;
    private Integer author_id;


//    @ManyToOne
//    @JoinColumn(name = "book_isbn")
//    private Book book;
//
//    public Book getBook() {
//        return book;
//    }
//    @ManyToOne(optional=false)
//    @JoinColumn(name = "author_id",insertable=false, updatable=false)
//    private Author author;

    public Integer getAuthorId() {
        return author_id;
    }

    public void setAuthorId(Integer authorId) {
        this.author_id = authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

//    public Author getAuthor() {
//        return author;
//    }
}