package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.services.BookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/bookcollection")
public class BookAuthorController {

    @Autowired
    private final BookAuthorService bookAuthorService;

    public BookAuthorController(BookAuthorService bookAuthorService){
        this.bookAuthorService = bookAuthorService;
    }

    @GetMapping("/books/{isbn}/authors")
    public CollectionModel<EntityModel<Author>> getAuthors(@PathVariable String isbn){
        return bookAuthorService.get(isbn);
    }
}
