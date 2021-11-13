package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.author.Author;
import com.bookstore.bookstore.model.bookauthor.BookAuthor;
import com.bookstore.bookstore.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="api/bookcollection")

public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @Operation(summary = "Get authors from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the authors", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "404", description = "Authors not found", content = @Content) })

    @GetMapping("/authors")

    public CollectionModel<EntityModel<Author>> getAuthors(@Parameter(description = "Last name of author to be searched")
                                                               @RequestParam(name="last_name")Optional<String> last_name,
                                                           @Parameter(description = "If the value of parameter is equal to value `exact` " +
                                                                   "the result of the search operation will be a perfect match")
                                                                @RequestParam(name="match") Optional<String> match){
        return authorService.getAllAuthors(last_name.orElse(""),match.orElse(""));
    }

    @Operation(summary = "Search an author by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find the author", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content) })
    @GetMapping("/author/{id}")

    public EntityModel<Author> getAuthorById(@Parameter(description = "id of author to be searched")
                                                 @PathVariable Integer id) {
        return authorService.getOneAuthor(id);
    }

    @Operation(summary = "Create a new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "406", description = "Could not create the author", content = @Content) })

    @PostMapping("/author")

    public ResponseEntity<?> createAuthor(@RequestBody Author newAuthor) {
        return authorService.createNewAuthor(newAuthor);
    }

    @Operation(summary = "Update an author by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update the author", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content),
            @ApiResponse(responseCode = "406", description = "Not acceptable", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content)})

    @PutMapping("/author/{id}")

    public ResponseEntity<?> updateAuthor(@RequestBody Author newAuthor, @Parameter(description = "id of author to be updated") @PathVariable Integer id) {
        return authorService.updateAuthor(newAuthor,id);
    }

    @Operation(summary = "Delete an author by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the author", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content) })

    @DeleteMapping("/author/{id}")

    public ResponseEntity<?> deleteAuthor(@Parameter(description = "id of author to be deleted") @PathVariable Integer id) {
       return authorService.deleteAuthorById(id);
    }

    @Operation(summary = "Search the authors of a a book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find the authors", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BookAuthor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid isbn", content = @Content),
            @ApiResponse(responseCode = "404", description = "Authors ot found", content = @Content) })
    @GetMapping("/books/{isbn}/authors")
    public CollectionModel<EntityModel<Author>> getAuthorsBooks(@Parameter(description = "isbn of book to be searched")
                                                           @PathVariable String isbn){
        return authorService.getAuthorsBooks(isbn);
    }
}
