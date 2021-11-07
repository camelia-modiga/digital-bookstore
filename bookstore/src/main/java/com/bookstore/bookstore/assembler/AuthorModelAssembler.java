package com.bookstore.bookstore.assembler;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.services.AuthorService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {
    @Override
    public EntityModel<Author> toModel(Author author) {
        return EntityModel.of(author,
                linkTo(methodOn(AuthorService.class).getOneAuthor(author.getId())).withSelfRel(),
                linkTo(methodOn(AuthorService.class).getAllAuthors("","")).withRel("authors"));
    }
}

