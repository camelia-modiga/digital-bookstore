package com.bookstore.bookstore.interfaces;

import com.bookstore.bookstore.model.author.Author;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface IAuthor {

    ResponseEntity<?>  getAllAuthors(String last_name,String match);

    EntityModel<Author> getOneAuthor(Integer id);

    ResponseEntity<?> createNewAuthor(Author newAuthor);

    ResponseEntity<?> updateAuthor(Author newAuthor, Integer id);

    ResponseEntity<?> deleteAuthorById(Integer id);

    CollectionModel<EntityModel<Author>> getAuthorsBooks(String isbn);
}
