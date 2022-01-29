package com.bookstore.bookstore.interfaces;

import com.bookstore.bookstore.model.book.Book;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface IBook {

    CollectionModel<EntityModel<Book>> getAllBooks(String genre,Integer year,int page, int items_per_page);

    EntityModel<?> getOneBook(String isbn);

    EntityModel<?> getBookPartialInformation(String isbn);

    ResponseEntity<?> createNewBook(Book newBook);

    ResponseEntity<?> updateBook(Book newBook, String isbn);

    ResponseEntity<?> deleteBookByIsbn(String isbn);
}
