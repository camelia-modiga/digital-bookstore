package com.bookstore.bookstore.model.author;

import com.bookstore.bookstore.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query(value = "SELECT * FROM book b WHERE b.genre = :genre", nativeQuery = true)
    List<Book> findByName(String genre);
}
