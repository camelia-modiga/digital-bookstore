package com.bookstore.bookstore.model.bookauthor;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookAuthorId implements Serializable {
    private static final long serialVersionUID = -8541276135677500003L;
    @Column(name = "author_index", nullable = false)
    private Integer authorIndex;
    @Column(name = "isbn", nullable = false, length = 50)
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAuthorIndex() {
        return authorIndex;
    }

    public void setAuthorIndex(Integer authorIndex) {
        this.authorIndex = authorIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, authorIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookAuthorId entity = (BookAuthorId) o;
        return Objects.equals(this.isbn, entity.isbn) &&
                Objects.equals(this.authorIndex, entity.authorIndex);
    }
}