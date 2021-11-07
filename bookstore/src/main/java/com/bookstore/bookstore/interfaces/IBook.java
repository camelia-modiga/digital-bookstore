package com.bookstore.bookstore.interfaces;

import com.bookstore.bookstore.model.book.Book;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IBook {
    CollectionModel<EntityModel<Book>> getAllBooks();

    EntityModel<Book> getOneBook(String isbn);

    CollectionModel<Book> getAllBooksByGenre(String genre);

    CollectionModel<Book> getAllBooksByYear(Integer year);

    CollectionModel<Book> getAllBooksByGenreAndYear(String genre, Integer year);

    EntityModel<IFilteredBook> getBookPartialInformation(String isbn);

    Book createNewBook(Book newBook);

    Book updateBook(Book newBook, String isbn);

    void deleteBookByIsbn(String isbn);

    ResponseEntity<Map<String, Object>> getBooksPerPage(int page, int items_per_page);
}
