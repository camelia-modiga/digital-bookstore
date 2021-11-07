package com.bookstore.bookstore.assembler;

import com.bookstore.bookstore.model.book.Book;
import com.bookstore.bookstore.services.BookService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {
    @Override
    public EntityModel<Book> toModel(Book book) {
        return EntityModel.of(book,
                linkTo(methodOn(BookService.class).getOneBook(book.getIsbn())).withSelfRel(),
                linkTo(methodOn(BookService.class).getAllBooks("",0)).withRel("books"));
    }
}
