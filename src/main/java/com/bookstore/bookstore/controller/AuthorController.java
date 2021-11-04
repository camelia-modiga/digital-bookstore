package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/bookcollection")

public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public List<Author> getAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("/author/{id}")
    public Author getAuthorById(@PathVariable Integer id) {
        return authorService.getOneAuthor(id);
    }

    @PostMapping("/author")
    public Author createAuthor(@RequestBody Author newAuthor) {
        return authorService.createNewAuthor(newAuthor);
    }

    @PutMapping("/author/{id}")
    public Author updateAuthor(@RequestBody Author newAuthor, @PathVariable Integer id) {
        return authorService.updateAuthor(newAuthor,id);
    }

    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }
}
