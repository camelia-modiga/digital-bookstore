package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.book.Book;
import com.bookstore.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/bookcollection")

public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("book/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookService.getOneBook(isbn);
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
