package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.bookauthor.BookAuthor;
import com.bookstore.bookstore.services.AuthorService;
import com.bookstore.bookstore.services.BookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/bookcollection")
public class BookAuthorController {

    @Autowired
    private final AuthorService bookAuthorService;

    public BookAuthorController(AuthorService bookAuthorService){
        this.bookAuthorService = bookAuthorService;
    }




}
