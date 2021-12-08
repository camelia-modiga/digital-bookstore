package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.book.Book;
import com.bookstore.bookstore.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@Tag(name = "Books", description = "The books API")
public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @Operation(summary = "Get books from database",
    tags = {"Books"},
            responses = {
            @ApiResponse(responseCode = "200", description = "Found the books", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "404", description = "Books not found", content = @Content)
    }
    )

    @RequestMapping(value="api/bookcollection/books", method = RequestMethod.GET,produces = "application/json")

    public CollectionModel<EntityModel<Book>> getBooks( @Parameter(description = "genre of book to be searched")
                                                            @RequestParam(name="genre") Optional<String> genre,
                                                        @Parameter(description = "year of book to be searched")
                                                            @RequestParam(name="year") Optional<Integer> year,
                                                        @Parameter(description = "Zero-based page index")
                                                            @RequestParam(defaultValue = "0") Optional<Integer> page,
                                                        @Parameter(description = "The size of the page to be returned")
                                                            @RequestParam(defaultValue = "3") Optional<Integer> items_per_page){
        return bookService.getAllBooks(genre.orElse(""),year.orElse(0),page.orElse(0),items_per_page.orElse(3));
    }

    @Operation(summary = "Search a book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find the book", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid isbn", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })

    @RequestMapping(value="api/bookcollection/book/{isbn}", method = RequestMethod.GET)

    public EntityModel<?> getBookByIsbn(@Parameter(description = "isbn of book to be searched. Cannot be empty")
                                               @PathVariable String isbn,
                                        @Parameter(description = "If the value of parameter is equal to value `false` "+
                                                "the search operation will show only some details of the book")
                                                @RequestParam(name = "verbose") Optional<Boolean> verbose) {
        if(verbose.isEmpty())
            return bookService.getOneBook(isbn);
        else
            return bookService.getBookPartialInformation(isbn);
    }


    @Operation(summary = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "406", description = "Could not create the book", content = @Content) })

    @RequestMapping(value="api/bookcollection/book", method = RequestMethod.POST)

    public ResponseEntity<?> createBook(@RequestBody Book newBook) {
        return bookService.createNewBook(newBook);
    }

    @Operation(summary = "Update a book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update the book", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid isbn", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content),
            @ApiResponse(responseCode = "406", description = "Not acceptable", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
    })

    @RequestMapping(value="api/bookcollection/book/{isbn}", method = RequestMethod.PUT)

    public ResponseEntity<?> updateBook(@RequestBody Book newBook, @Parameter(description = "isbn of book to be updated. Cannot be empty") @PathVariable String isbn) {
        return bookService.updateBook(newBook,isbn);
    }

    @Operation(summary = "Delete a book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the book", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid isbn", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })

    @RequestMapping(value="api/bookcollection/book/{isbn}", method = RequestMethod.DELETE)

    public ResponseEntity<?> deleteBook(@Parameter(description = "isbn of book to be deleted. Cannot be empty") @PathVariable String isbn) {
        return bookService.deleteBookByIsbn(isbn);
    }
}
