package com.bookstore.bookstore.assembler;

import com.bookstore.bookstore.controller.BookController;
import com.bookstore.bookstore.model.book.Book;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {
    @Override
    public EntityModel<Book> toModel(Book book) {
        return EntityModel.of(book,
                linkTo(methodOn(BookController.class).getBookByIsbn(book.getIsbn(), Optional.of(true))).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks(Optional.of(book.getGenre()),Optional.of(book.getYear()),Optional.of(0),Optional.of(3))).withRel("books"));
    }
}
