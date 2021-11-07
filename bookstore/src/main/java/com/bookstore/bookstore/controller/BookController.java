package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.interfaces.IFilteredBook;
import com.bookstore.bookstore.model.book.Book;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="api/bookcollection")

public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @Operation(summary = "Get all books from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found books",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "404", description = "Books not found",
                    content = @Content) })
    @GetMapping("/books")
    public CollectionModel<EntityModel<Book>> getBooks(){
        return bookService.getAllBooks();
    }

    @Operation(summary = "Get a book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid isbn supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @GetMapping("/book/{isbn}")
    public EntityModel<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getOneBook(isbn);
    }

    @GetMapping(value="/books/filter")
    public ResponseEntity<Map<String, Object>> getBooksPerPage(
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int items_per_page) {
        return bookService.getBooksPerPage(page, items_per_page);
    }


    @Operation(summary = "Get all books by a specific genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid genre supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @GetMapping(value="/books", params="genre")
    public CollectionModel<Book> getBookByGenre(@RequestParam(name="genre",required = false) String genre ) {
        return bookService.getAllBooksByGenre(genre);
    }

    @Operation(summary = "Get all books by a specific year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid year supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @GetMapping(value="/books", params="year")
    public CollectionModel<Book> getBooksByYear(@RequestParam Integer year) {
        return bookService.getAllBooksByYear(year);
    }

    @Operation(summary = "Get all books by a specific genre and year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid year or genre supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @GetMapping(value="/books", params={"genre","year"})
    public CollectionModel<Book> getBooksByGenreAndYear(@RequestParam String genre, @RequestParam Integer year) {
        return bookService.getAllBooksByGenreAndYear(genre,year);
    }

    @GetMapping(value="/bo/{isbn}")//, params={"verbose"})
    public EntityModel<IFilteredBook> getPartialInformation(@PathVariable String isbn){

    //}, @RequestParam(value = "verbose",defaultValue = "false") String verbose) {
        return bookService.getBookPartialInformation(isbn);
    }

    @PostMapping("/book")
    public Book createBook(@RequestBody Book newBook) {
        return bookService.createNewBook(newBook);
    }

    @PutMapping("/book/{isbn}")
    public Book updateBook(@RequestBody Book newBook, @Parameter(description = "isbn of book to be updated") @PathVariable String isbn) {
        return bookService.updateBook(newBook,isbn);
    }

    @Operation(summary = "Delete a book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorService.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid isbn",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @DeleteMapping("/book/{isbn}")
    public void deleteBook(@Parameter(description = "isbn of book to be deleted") @PathVariable String isbn) {
        bookService.deleteBookByIsbn(isbn);
    }
}
