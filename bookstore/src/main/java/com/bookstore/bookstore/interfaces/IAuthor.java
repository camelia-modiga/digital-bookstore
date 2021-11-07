package com.bookstore.bookstore.interfaces;

import com.bookstore.bookstore.model.author.Author;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface IAuthor {
    CollectionModel<EntityModel<Author>> getAllAuthors();

    EntityModel<Author> getOneAuthor(Integer id);

    CollectionModel<Author> getAllAuthorsByNamePartialMatch(String last_name);

    CollectionModel<Author> getAllAuthorsByNamePerfectMatch(String last_name);

    Author createNewAuthor(Author newAuthor);

    Author updateAuthor(Author newAuthor, Integer id);

    void deleteAuthorById(Integer id);
}