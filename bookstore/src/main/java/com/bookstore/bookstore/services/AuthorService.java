package com.bookstore.bookstore.services;

import com.bookstore.bookstore.assembler.AuthorModelAssembler;
import com.bookstore.bookstore.interfaces.IAuthor;
import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.author.AuthorRepository;
import com.bookstore.bookstore.exceptions.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class AuthorService implements IAuthor {

    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private final AuthorModelAssembler assembler;

    public AuthorService(AuthorRepository authorRepository,AuthorModelAssembler assembler){
        this.authorRepository = authorRepository;
        this.assembler=assembler;
    }

    public CollectionModel<EntityModel<Author>> getAllAuthors(String last_name,String match) {
        List<Author> authors=authorRepository.findAll();
        if(!Objects.equals(last_name, "") && Objects.equals(match, "exact"))
            authors=authors.stream().filter(author -> author.getLastName().matches(last_name)).collect(Collectors.toList());
        if(!Objects.equals(last_name, ""))
            authors=authors.stream().filter(author ->author.getLastName().contains(last_name)).collect(Collectors.toList());
        List<EntityModel<Author>> final_list=authors.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(final_list, linkTo(methodOn(AuthorService.class).getAllAuthors("","")).withSelfRel());
    }

    public EntityModel<Author> getOneAuthor(Integer id) {
        Author author =authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
        return assembler.toModel(author);
    }


    public ResponseEntity<?> createNewAuthor(Author newAuthor) {
        EntityModel<Author> entityModel=assembler.toModel(authorRepository.save(newAuthor));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<?> updateAuthor(Author newAuthor, Integer id) {

       Author updatedAuthor=authorRepository.findById(id)
                .map(author -> {
                    author.setFirstName(newAuthor.getFirstName());
                    author.setLastName(newAuthor.getLastName());
                    return authorRepository.save(author);
                })
                .orElseGet(() -> {
                    newAuthor.setId(id);
                    return authorRepository.save(newAuthor);
                });
        EntityModel<Author> entityModel = assembler.toModel(updatedAuthor);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<?>  deleteAuthorById(Integer id) {
        authorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
