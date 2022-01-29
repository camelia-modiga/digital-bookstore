package com.bookstore.bookstore.model.author;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Table(name = "author", indexes = {
        @Index(name = "author_name_uk", columnList = "first_name, last_name", unique = true),
        @Index(name = "first_name_idx", columnList = "first_name"),
        @Index(name = "last_name_idx", columnList = "last_name")
})
@Entity
public class Author {
    @Schema(description = "Unique identifier of the author.", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Schema(description = "First name of the author.", example = "Jessica", required = true)
    @Size(max = 50)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Schema(description = "Last name of the author.", example = "Abigail", required = true)
    @Size(max = 50)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}