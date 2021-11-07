package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.services.AuthorService;
import com.bookstore.bookstore.services.BookAuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Search the authors of a a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find the authors",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid isbn",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @GetMapping("/book/{isbn}/authors")
    public CollectionModel<EntityModel<Author>> getAuthors(@Parameter(description = "isb of book to be searched")
                                                               @PathVariable String isbn){
        return bookAuthorService.get(isbn);
    }
}
