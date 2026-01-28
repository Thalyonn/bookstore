package com.htd.bookstore.service;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import com.htd.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Book> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}
