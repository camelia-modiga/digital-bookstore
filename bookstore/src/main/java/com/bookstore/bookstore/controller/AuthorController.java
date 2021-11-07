package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.services.AuthorService;
import com.bookstore.bookstore.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/bookcollection")

public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @Operation(summary = "Get all authors from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found authors",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "404", description = "Authors not found",
                    content = @Content) })
    @GetMapping("/authors")
    public CollectionModel<EntityModel<Author>> getAuthors(){
        return authorService.getAllAuthors();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the author",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = @Content) })
    @GetMapping("/author/{id}")
    public EntityModel<Author> getAuthorById(@PathVariable Integer id) {
        return authorService.getOneAuthor(id);
    }

    @GetMapping(value="/authors", params="name")
    public CollectionModel<Author> getAuthorByNamePartialMatch(@RequestParam(name="name",required = false)  String last_name) {
        return authorService.getAllAuthorsByNamePartialMatch(last_name);
    }

    @Operation(summary = "Get all authors from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found authors",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "404", description = "Authors not found",
                    content = @Content) })
    @GetMapping(value="/authors", params={"name","match"})
    public CollectionModel<Author> getAuthorByNamePerfectMatch(@RequestParam(name="name")  String last_name,@RequestParam(value = "match",defaultValue = "exact") String match) {
        return authorService.getAllAuthorsByNamePerfectMatch(last_name);
    }

    @PostMapping("/author")
    public Author createAuthor(@RequestBody Author newAuthor) {
        return authorService.createNewAuthor(newAuthor);
    }

    @PutMapping("/author/{id}")
    public Author updateAuthor(@RequestBody Author newAuthor, @Parameter(description = "id of author to be updated") @PathVariable Integer id) {
        return authorService.updateAuthor(newAuthor,id);
    }

    @Operation(summary = "Delete an author his its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the author",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = @Content) })
    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@Parameter(description = "id of author to be deleted") @PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }

}
