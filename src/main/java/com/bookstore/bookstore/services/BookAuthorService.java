package com.bookstore.bookstore.services;

import com.bookstore.bookstore.controller.BookAuthorController;
import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.bookauthor.BookAuthor;
import com.bookstore.bookstore.model.bookauthor.BookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookAuthorService {

    @Autowired
    private final BookAuthorRepository bookAuthorRepository;

    public BookAuthorService(BookAuthorRepository bookAuthorRepository){
        this.bookAuthorRepository = bookAuthorRepository;
    }

}
