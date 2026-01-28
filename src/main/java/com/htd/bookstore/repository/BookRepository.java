package com.htd.bookstore.repository;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContainingIgnoreCase(String keyword);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByCategory(Category category);
}
