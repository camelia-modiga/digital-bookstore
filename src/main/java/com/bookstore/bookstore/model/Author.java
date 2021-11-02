package com.bookstore.bookstore.model;

import javax.persistence.*;

@Table(name = "author", indexes = {
        @Index(name = "idx_author_firstName", columnList = "lastName"),
        @Index(name = "idx_author_lastName", columnList = "fistName")
})
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fistName", nullable = false, length = 45)
    private String fistName;

    @Column(name = "lastName", length = 45)
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}