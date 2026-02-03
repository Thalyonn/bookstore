package com.htd.bookstore.service;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import com.htd.bookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Gets all books.
     * @return List<Book> of all books.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    /**
     * Gets books by ID.
     * @param id The id of the book to get.
     * @return Optional<Book>
     */
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Searches for books by title given a keyword.
     * @param keyword The keyword of the title of the books to find.
     * @return A List of Book.
     */
    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Gets books by category.
     * @param category The category of the books to get.
     * @return A list of books with the category given.
     */
    public Page<Book> getBooksByCategory(Category category, Pageable pageable) {
        return bookRepository.findByCategory(category, pageable);
    }

    /**
     * Adds a book to the repository.
     * @param book The book to add to the repository.
     * @return The added Book.
     */
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Searches for books given a keyword and category.
     * @param keyword Keyword of the title to find.
     * @param category Category of the book to find.
     * @return List of books matching the keyword and category criteria.
     */
    public Page<Book> searchBooksByCategory(String keyword, Category category, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCaseAndCategory(keyword, category, pageable);

    }

    /**
     * A method to unify the other search methods by Title keyword and Category.
     * @param keyword Keyword of the title to find.
     * @param category Category of the book to find.
     * @return List of books matching the keyword or category criteria.
     */
    public Page<Book> filterBooks(String keyword, Category category, Pageable pageable) {
        if (keyword != null && !keyword.isBlank() && category != null) {
            return searchBooksByCategory(keyword, category, pageable);
        } else if (keyword != null && !keyword.isBlank()) {
            return searchBooks(keyword, pageable);
        } else if (category != null) {
            return getBooksByCategory(category, pageable);
        } else {
            return getAllBooks(pageable);
        }
    }
}
