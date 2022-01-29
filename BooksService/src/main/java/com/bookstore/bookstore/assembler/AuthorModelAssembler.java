package com.bookstore.bookstore.assembler;

import com.bookstore.bookstore.controller.AuthorController;
import com.bookstore.bookstore.model.author.Author;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {
    @Override
    public EntityModel<Author> toModel(Author author) {
        return EntityModel.of(author,
                linkTo(methodOn(AuthorController.class).getAuthorById(author.getId())).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAuthors(Optional.of(author.getLastName()),Optional.of(""))).withRel("authors"));
    }
}

