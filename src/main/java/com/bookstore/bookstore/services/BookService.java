package com.bookstore.bookstore.services;

import com.bookstore.bookstore.model.book.Book;
import com.bookstore.bookstore.model.book.BookRepository;
import com.bookstore.bookstore.services.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    @Autowired

    private final BookRepository bookRepository;
    public BookService(BookRepository authorRepository){
        this.bookRepository = authorRepository;
    }

    public CollectionModel<EntityModel<Book>> getAllBooks() {
        List<EntityModel<Book>> books = bookRepository.findAll().stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookService.class).getOneBook(book.getIsbn())).withSelfRel(),
                        linkTo(methodOn(BookService.class).getAllBooks()).withRel("books")))
                .collect(Collectors.toList());

        return CollectionModel.of(books, linkTo(methodOn(BookService.class).getAllBooks()).withSelfRel());
    }

    public EntityModel<Book> getOneBook(String isbn) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        return EntityModel.of(book,
                linkTo(methodOn(BookService.class).getOneBook(isbn)).withSelfRel(),
                linkTo(methodOn(BookService.class).getAllBooks()).withRel("books"));
    }

    public Book createNewBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    public Book updateBook(Book newBook, String isbn) {
        if(bookRepository.findById(isbn).isEmpty())
            throw new BookNotFoundException(isbn);
        else return bookRepository.findById(isbn)
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
