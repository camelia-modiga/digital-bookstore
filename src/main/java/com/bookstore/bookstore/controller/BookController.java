package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.book.Book;
import com.bookstore.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/bookcollection")

public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public CollectionModel<EntityModel<Book>> getBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{isbn}")
    public EntityModel<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getOneBook(isbn);
    }

    @RequestMapping(value="/books", params="genre", method=RequestMethod.GET)
    public CollectionModel<Book> getBookByGenre(@RequestParam String genre) {
        return bookService.getAllBooksByGenre(genre);
    }

    @RequestMapping(value="/books", params="year", method=RequestMethod.GET)
    public CollectionModel<Book> getBooksByYear(@RequestParam Integer year) {
        return bookService.getAllBooksByYear(year);
    }

    @RequestMapping(value="/books", params={"genre","year"}, method=RequestMethod.GET)
    public CollectionModel<Book> getBooksByGenreAndYear(@RequestParam String genre, @RequestParam Integer year) {
        return bookService.getAllBooksByGenreAndYear(genre,year);
    }

    @PostMapping("/book")
    public Book createBook(@RequestBody Book newBook) {
        return bookService.createNewBook(newBook);
    }

    @PutMapping("/book/{isbn}")
    public Book updateBook(@RequestBody Book newBook, @PathVariable String isbn) {
        return bookService.updateBook(newBook,isbn);
    }

    @DeleteMapping("/book/{isbn}")
    public void deleteBook(@PathVariable String isbn) {
        bookService.deleteBookByIsbn(isbn);
    }
}
