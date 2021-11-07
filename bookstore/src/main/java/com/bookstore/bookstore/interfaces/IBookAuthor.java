package com.bookstore.bookstore.interfaces;

import com.bookstore.bookstore.model.author.Author;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface IBookAuthor {
    CollectionModel<EntityModel<Author>> get(String isbn);
}
