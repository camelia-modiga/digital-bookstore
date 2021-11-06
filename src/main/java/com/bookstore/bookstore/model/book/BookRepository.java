package com.bookstore.bookstore.model.book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query(value = "SELECT * FROM book b WHERE b.genre = :genre", nativeQuery = true)
    List<Book> findByGenre(String genre);

    @Query(value = "SELECT * FROM book b WHERE b.year = :year", nativeQuery = true)
    List<Book> findByYear(Integer year);

    @Query(value = "SELECT * FROM book b WHERE b.genre=:genre and b.year = :year", nativeQuery = true)
    List<Book> findByGenreAndYear(String genre,Integer year);
}
