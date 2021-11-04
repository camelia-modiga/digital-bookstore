package com.bookstore.bookstore.services;

import com.bookstore.bookstore.model.book.Book;
import com.bookstore.bookstore.model.book.BookRepository;
import com.bookstore.bookstore.services.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired

    private final BookRepository bookRepository;
    public BookService(BookRepository authorRepository){
        this.bookRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getOneBook(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book createNewBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    public Book updateBook(Book newBook, String isbn) {

        return bookRepository.findById(isbn)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setPublisher(newBook.getPublisher());
                    book.setYear(newBook.getYear());
                    book.setGenre(newBook.getGenre());
                    return bookRepository.save(book);
                })
                .orElseGet(() -> {
                    newBook.setIsbn(isbn);
                    return bookRepository.save(newBook);
                });
    }

    public void deleteBookByIsbn(String isbn) {
        if(bookRepository.findById(isbn).isEmpty())
            throw new BookNotFoundException(isbn);
        bookRepository.deleteById(isbn);
    }
}
