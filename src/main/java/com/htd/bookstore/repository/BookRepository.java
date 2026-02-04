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
     * Find by title containing keyword.
     *
     * @param keyword  the keyword
     * @param pageable the pageable
     * @return the page
     */
    Page<Book> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    /**
     * Find by author.
     *
     * @param author the author
     * @return the list
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);


    /**
     * Find by category page.
     *
     * @param category the category
     * @param pageable the pageable
     * @return the page
     */
    Page<Book> findByCategory(Category category, Pageable pageable);


    /**
     * Find by title containing keyword and category.
     *
     * @param keyword  the keyword
     * @param category the category
     * @param pageable the pageable
     * @return the page
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
