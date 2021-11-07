package com.bookstore.bookstore.interfaces;

import com.bookstore.bookstore.model.author.Author;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface IAuthor {

    CollectionModel<EntityModel<Author>> getAllAuthors(String last_name,String match);

    EntityModel<Author> getOneAuthor(Integer id);

    Author createNewAuthor(Author newAuthor);

    Author updateAuthor(Author newAuthor, Integer id);

    void deleteAuthorById(Integer id);
}
