package com.bookstore.bookstore.model.book;

import com.bookstore.bookstore.interfaces.IFilteredBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query(value = "SELECT isbn, title, genre from book b where b.isbn=:isbn", nativeQuery = true)
    Optional<IFilteredBook> partialFind(String isbn);
}
