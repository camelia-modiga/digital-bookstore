package com.bookstore.bookstore.services;

import com.bookstore.bookstore.interfaces.IAuthor;
import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.author.AuthorRepository;
import com.bookstore.bookstore.exceptions.AuthorNotFoundException;
import com.bookstore.bookstore.model.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class AuthorService implements IAuthor {

    @Autowired
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public CollectionModel<EntityModel<Author>> getAllAuthors(String last_name,String match) {
        List<Author> authors=authorRepository.findAll();
        if(!Objects.equals(last_name, "") && Objects.equals(match, "exact"))
            authors=authors.stream().filter(author -> author.getLastName().matches(last_name)).collect(Collectors.toList());
        if(!Objects.equals(last_name, ""))
            authors=authors.stream().filter(author ->author.getLastName().contains(last_name)).collect(Collectors.toList());
        List<EntityModel<Author>> final_list=authors.stream()
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(AuthorService.class).getOneAuthor(author.getId())).withSelfRel(),
                        linkTo(methodOn(AuthorService.class).getAllAuthors("","")).withRel("authors")))
                .collect(Collectors.toList());
        return CollectionModel.of(final_list, linkTo(methodOn(AuthorService.class).getAllAuthors("","")).withSelfRel());
    }

    public EntityModel<Author> getOneAuthor(Integer id) {
        Author author =authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
        return EntityModel.of(author,
                linkTo(methodOn(AuthorService.class).getOneAuthor(id)).withSelfRel(),
                linkTo(methodOn(AuthorService.class).getAllAuthors("","")).withRel("authors"));
    }


    public Author createNewAuthor(Author newAuthor) {
        return authorRepository.save(newAuthor);
    }

    public Author updateAuthor(Author newAuthor, Integer id) {

        if(authorRepository.findById(id).isEmpty())
            throw new AuthorNotFoundException();
        else return authorRepository.findById(id)
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
            throw new AuthorNotFoundException();
        authorRepository.deleteById(id);
    }
}
