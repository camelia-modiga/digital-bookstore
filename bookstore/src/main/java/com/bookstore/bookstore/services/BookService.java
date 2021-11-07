package com.bookstore.bookstore.services;

import com.bookstore.bookstore.interfaces.IBook;
import com.bookstore.bookstore.interfaces.IFilteredBook;
import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.book.Book;
import com.bookstore.bookstore.model.book.BookRepository;
import com.bookstore.bookstore.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService implements IBook {

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository authorRepository){
        this.bookRepository = authorRepository;
    }

    public CollectionModel<EntityModel<Book>> getAllBooks(String genre, Integer year) {
        List<Book> books = bookRepository.findAll();

        if(!Objects.equals(genre, ""))
            books=books.stream().filter(book -> book.getGenre().matches(String.valueOf(genre))).collect(Collectors.toList());
        if(year!=0)
            books=books.stream().filter(book-> book.getYear().toString().matches(year.toString())).collect(Collectors.toList());
        List<EntityModel<Book>> final_list=books.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookService.class).getOneBook(book.getIsbn())).withSelfRel(),
                        linkTo(methodOn(BookService.class).getAllBooks("",0)).withRel("books")))
                .collect(Collectors.toList());
        return CollectionModel.of(final_list, linkTo(methodOn(BookService.class).getAllBooks("",0)).withSelfRel());
    }

        public EntityModel<Book> getOneBook(String isbn) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(BookNotFoundException::new);
        return EntityModel.of(book,
                linkTo(methodOn(BookService.class).getOneBook(isbn)).withSelfRel(),
                linkTo(methodOn(BookService.class).getAllBooks("",0)).withRel("books"));
    }

//    public EntityModel<IFilteredBook> getBookPartialInformation(String isbn){
//        IFilteredBook book = bookRepository.partialFind(isbn)
//                .orElseThrow(BookNotFoundException::new);
//        return EntityModel.of(book,
//                linkTo(methodOn(BookService.class).getBookPartialInformation(isbn)).withSelfRel(),
//                linkTo(methodOn(BookService.class).getAllBooks("",0)).withRel("books"));
//    }

//    public EntityModel<?> getOneBook(String isbn,String verbose) {
//        Optional<IFilteredBook> book;
//        Optional<Book> book2;
//        if(!isbn.equals(""))
//            book2=bookRepository.findById(isbn);
//        if(verbose=="false")
//            book=bookRepository.partialFind(isbn);
//        return EntityModel.of(book,
//                linkTo(methodOn(BookService.class).getOneBook(isbn)).withSelfRel(),
//                linkTo(methodOn(BookService.class).getAllBooks("",0)).withRel("books"));
//    }

    public Book createNewBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    public Book updateBook(Book newBook, String isbn) {
        if(bookRepository.findById(isbn).isEmpty())
            throw new BookNotFoundException();
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
            throw new BookNotFoundException();
        bookRepository.deleteById(isbn);
    }

    public ResponseEntity<Map<String, Object>> getBooksPerPage(int page, int items_per_page){
        try {
            List<Book> book;
            Pageable paging = PageRequest.of(page, items_per_page);
            Page<Book> pageBook;

            pageBook = bookRepository.findAll(paging);
            book = pageBook.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("books", book);
            response.put("currentPage", pageBook.getNumber());
            response.put("totalItems", pageBook.getTotalElements());
            response.put("totalPages", pageBook.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
