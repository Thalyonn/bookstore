package com.htd.bookstore.service;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import com.htd.bookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
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
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    /**
     * Gets books by category.
     * @param category The category of the books to get.
     * @return A list of books with the category given.
     */
    public List<Book> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category);
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
}
