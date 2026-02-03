package com.htd.bookstore.repository;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The interface Book repository.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Find by title containing keyword and ignore case list.
     *
     * @param keyword the keyword
     * @return the list
     */
    Page<Book> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    /**
     * Find by author containing ignore case list.
     *
     * @param author the author
     * @return the list
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);

    /**
     * Find by category list.
     *
     * @param category the category
     * @return the list
     */
    Page<Book> findByCategory(Category category, Pageable pageable);

    /**
     * Find by title containing ignore case and category list.
     *
     * @param keyword  the keyword
     * @param category the category
     * @return the list
     */
    Page<Book> findByTitleContainingIgnoreCaseAndCategory(String keyword, Category category, Pageable pageable);

    /**
     * Gets books by book id.
     *
     * @param bookId the book id
     * @return the books by book id
     */
    Book getBooksByBookId(Long bookId);
}
