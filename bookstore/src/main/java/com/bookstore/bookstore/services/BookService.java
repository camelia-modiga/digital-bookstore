package com.bookstore.bookstore.services;

import com.bookstore.bookstore.assembler.BookModelAssembler;
import com.bookstore.bookstore.interfaces.IBook;
import com.bookstore.bookstore.model.book.Book;
import com.bookstore.bookstore.model.book.BookRepository;
import com.bookstore.bookstore.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService implements IBook {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final BookModelAssembler assembler;

    public BookService(BookRepository authorRepository,BookModelAssembler assembler){
        this.bookRepository = authorRepository;
        this.assembler=assembler;
    }

    public CollectionModel<EntityModel<Book>> getAllBooks(String genre, Integer year) {
        List<Book> books = bookRepository.findAll();

        if(!Objects.equals(genre, ""))
            books=books.stream().filter(book -> book.getGenre().matches(String.valueOf(genre))).collect(Collectors.toList());
        if(year!=0)
            books=books.stream().filter(book-> book.getYear().toString().matches(year.toString())).collect(Collectors.toList());
        List<EntityModel<Book>> final_list=books.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(final_list, linkTo(methodOn(BookService.class).getAllBooks("",0)).withSelfRel());
    }

        public EntityModel<Book> getOneBook(String isbn) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(BookNotFoundException::new);
        return assembler.toModel(book);
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

    public ResponseEntity<?> createNewBook(Book newBook) {
        EntityModel<Book> entityModel = assembler.toModel(bookRepository.save(newBook));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<?> updateBook(Book newBook, String isbn) {

        Book updatedBook=bookRepository.findById(isbn)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setPublisher(newBook.getPublisher());
                    book.setYear(newBook.getYear());
                    book.setGenre(newBook.getGenre());
                    return bookRepository.save(book);
                })
                .orElseThrow(BookNotFoundException::new);
        EntityModel<Book> entityModel = assembler.toModel(updatedBook);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<?> deleteBookByIsbn(String isbn) {
        if(bookRepository.findById(isbn).isEmpty())
            throw new BookNotFoundException();
        else
            bookRepository.deleteById(isbn);
        return ResponseEntity.noContent().build();
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
