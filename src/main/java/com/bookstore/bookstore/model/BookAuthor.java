package com.bookstore.bookstore.model;

import javax.persistence.*;

@Table(name = "book_author", indexes = {
        @Index(name = "fk_ISBN_idx", columnList = "isbn"),
        @Index(name = "fk_id_author_idx", columnList = "author_id")
})
@Entity
public class BookAuthor {
    @Id
    @Column(name = "`index`", nullable = false)
    private Integer id;

    @Column(name = "isbn", nullable = false, length = 50)
    private String isbn;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}