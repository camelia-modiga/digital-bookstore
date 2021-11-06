package com.bookstore.bookstore.services;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.bookauthor.BookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookAuthorService {

    @Autowired
    private final BookAuthorRepository bookAuthorRepository;

    @Autowired
    private final AuthorService author;

    public BookAuthorService(BookAuthorRepository bookAuthorRepository,AuthorService author){
        this.bookAuthorRepository = bookAuthorRepository;
        this.author = author;
    }

    public CollectionModel<EntityModel<Author>> get(String isbn) {
        List<EntityModel<Author>> authors=bookAuthorRepository.findAll().stream()
                .filter(r->r.getIsbn().getIsbn().matches(isbn))
                .map(l->author.getOneAuthor(l.getAuthor().getId()))
                .collect(Collectors.toList());
        return CollectionModel.of(authors, linkTo(methodOn(AuthorService.class).getAllAuthors()).withSelfRel());
    }
}
