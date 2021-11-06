package com.bookstore.bookstore.services;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.author.AuthorRepository;
import com.bookstore.bookstore.model.book.Book;
import com.bookstore.bookstore.model.bookauthor.BookAuthor;
import com.bookstore.bookstore.model.bookauthor.BookAuthorRepository;
import com.bookstore.bookstore.services.exceptions.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class AuthorService {

    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private final BookAuthorRepository bookAuthorRepository;


    public AuthorService(AuthorRepository authorRepository,BookAuthorRepository bookAuthorRepository){
        this.authorRepository = authorRepository;
        this.bookAuthorRepository=bookAuthorRepository;
    }

//    public CollectionModel<EntityModel<Author>> get(String isbn) {
//        List<EntityModel<Author>> authors=bookAuthorRepository.findAll().stream()
//                .map(author -> EntityModel.of(author,
//                        linkTo(methodOn(BookAuthor.class).getBook())
//
//                //.filter(r->r.getIsbn().isEmpty())
//                //.map(l->getOneAuthor(l.getAuthorId()))
//               // .collect(Collectors.toList());
//        return CollectionModel.of(authors, linkTo(methodOn(AuthorService.class).getAllAuthors()).withSelfRel());
//    }

    public CollectionModel<EntityModel<Author>> getAllAuthors() {
        List<EntityModel<Author>> authors=authorRepository.findAll().stream()
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(AuthorService.class).getOneAuthor(author.getId())).withSelfRel(),
                        linkTo(methodOn(AuthorService.class).getAllAuthors()).withRel("authors")))
                .collect(Collectors.toList());
        return CollectionModel.of(authors, linkTo(methodOn(AuthorService.class).getAllAuthors()).withSelfRel());
    }

    public EntityModel<Author> getOneAuthor(Integer id) {
        Author author =authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        return EntityModel.of(author,
                linkTo(methodOn(AuthorService.class).getOneAuthor(id)).withSelfRel(),
                linkTo(methodOn(AuthorService.class).getAllAuthors()).withRel("authors"));
    }

    public Author createNewAuthor(Author newAuthor) {
        return authorRepository.save(newAuthor);
    }

    public Author updateAuthor(Author newAuthor, Integer id) {

        return authorRepository.findById(id)
                .map(author -> {
                    author.setFirstName(newAuthor.getFirstName());
                    author.setLastName(newAuthor.getLastName());
                    return authorRepository.save(author);
                })
                .orElseGet(() -> {
                    newAuthor.setId(id);
                    return authorRepository.save(newAuthor);
                });
    }

    public void deleteAuthorById(Integer id) {
        if(authorRepository.findById(id).isEmpty())
            throw new AuthorNotFoundException(id);
        authorRepository.deleteById(id);
    }
}
