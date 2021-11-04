package com.bookstore.bookstore.services;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.author.AuthorRepository;
//import com.bookstore.bookstore.model.bookauthor.BookAuthor;
//import com.bookstore.bookstore.model.bookauthor.BookAuthorRepository;
import com.bookstore.bookstore.services.exceptions.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorService {

//    @Autowired
//    private final BookAuthorRepository bookAuthorRepository;
//    public BookAuthorService(BookAuthorRepository bookAuthorRepository){
//        this.bookAuthorRepository = bookAuthorRepository;
//    }

//    public BookAuthor getBookByIsbnAndAuthor(Integer id) {
//        return bookAuthorRepository.findById(id);
//    }
}
