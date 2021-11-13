package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.book.Book;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="api/bookcollection")

public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @Operation(summary = "Get books from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the books", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "404", description = "Books not found", content = @Content) })

    @GetMapping(value="/books")

    public CollectionModel<EntityModel<Book>> getBooks( @Parameter(description = "genre of book to be searched")
                                                            @RequestParam(name="genre") Optional<String> genre,
                                                        @Parameter(description = "year of book to be searched")
                                                            @RequestParam(name="year") Optional<Integer> year){
        return bookService.getAllBooks(genre.orElse(""),year.orElse(0));
    }

    @Operation(summary = "Search a book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find the book", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid isbn", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })

    @GetMapping("/book/{isbn}")

    public EntityModel<?> getBookByIsbn(@Parameter(description = "isbn of book to be searched")
                                               @PathVariable String isbn,
                                        @RequestParam(name = "verbose") Optional<String> verbose) {
        if(verbose.isEmpty())
            return bookService.getOneBook(isbn);
        else
            return bookService.getBookPartialInformation(isbn);
    }


    @GetMapping(value="/books/filter")
    public ResponseEntity<Map<String, Object>> getBooksPerPage(
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int items_per_page) {
        return bookService.getBooksPerPage(page, items_per_page);
    }


    @Operation(summary = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "406", description = "Could not create the book", content = @Content) })

    @PostMapping("/book")

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

    @PutMapping("/book/{isbn}")

    public ResponseEntity<?> updateBook(@RequestBody Book newBook, @Parameter(description = "isbn of book to be updated") @PathVariable String isbn) {
        return bookService.updateBook(newBook,isbn);
    }

    @Operation(summary = "Delete a book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the book", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid isbn", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })

    @DeleteMapping("/book/{isbn}")

    public ResponseEntity<?> deleteBook(@Parameter(description = "isbn of book to be deleted") @PathVariable String isbn) {
        return bookService.deleteBookByIsbn(isbn);
    }
}
