package com.bookstore.bookstore.model.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query(value = "SELECT * FROM author a WHERE a.last_name LIKE %:last_name% ", nativeQuery = true)
    List<Author> findByNamePartialMatch(String last_name);

    @Query(value = "SELECT * FROM author a WHERE a.last_name = :last_name", nativeQuery = true)
    List<Author> findByNamePerfectMatch(String last_name);
}
