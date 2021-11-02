package com.bookstore.bookstore.repository;
import com.bookstore.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthorRepository, Book> {
}
