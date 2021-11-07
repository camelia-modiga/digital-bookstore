package com.bookstore.bookstore.interfaces;

import com.bookstore.bookstore.model.book.Book;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IBook {
    CollectionModel<EntityModel<Book>> getAllBooks(String genre,Integer year);

    EntityModel<Book> getOneBook(String isbn);

    //EntityModel<?> getOneBook(String isbn,String verbose);

    //EntityModel<IFilteredBook> getBookPartialInformation(String isbn);

    ResponseEntity<?> createNewBook(Book newBook);

    ResponseEntity<?> updateBook(Book newBook, String isbn);

    ResponseEntity<?> deleteBookByIsbn(String isbn);

    ResponseEntity<Map<String, Object>> getBooksPerPage(int page, int items_per_page);
}
