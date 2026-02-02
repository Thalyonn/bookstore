package com.htd.bookstore.controller;

import com.htd.bookstore.dto.BookResponse;
import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import com.htd.bookstore.service.BookService;
import com.htd.bookstore.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The book controller.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {
    /**
     * The Book service.
     */
    BookService bookService;
    /**
     * The Category service.
     */
    CategoryService categoryService;

    /**
     * Instantiates a new Book controller.
     *
     * @param bookService     the book service
     * @param categoryService the category service
     */
    public BookController(BookService bookService,  CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll() {
        List<Book> books = bookService.getAllBooks();
        List<BookResponse> responses = books.stream().map(BookResponse::new)
                .toList();
        return ResponseEntity.ok(responses);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            BookResponse bookResponse = book.map(BookResponse::new).orElse(null);
            return ResponseEntity.ok(bookResponse);
        }
        else  {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find by category response entity.
     *
     * @param category the category
     * @return the response entity
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookResponse>> findByCategory(@PathVariable String category) {
        List<Book> books = bookService.getBooksByCategory(categoryService.findByName(category));
        if(books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(books.stream().map(BookResponse::new).toList());
        }
    }

    /**
     * Find by title response entity of BookResponses.
     *
     * @param title the title
     * @return the response entity
     */
    @GetMapping("/search/{title}")
    public ResponseEntity<List<BookResponse>> findByTitle(@PathVariable String title) {
        List<Book> books = bookService.searchBooks(title);
        if(books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(books.stream().map(BookResponse::new).toList());
        }
    }


    /**
     * Filter books by search keyword and/or category.
     *
     * @param search   optional search keyword
     * @param category optional category name
     * @return list of book responses
     */
    @GetMapping("/filter")
    public ResponseEntity<List<BookResponse>> getBooks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category) {

        Category cat = null;
        if (category != null && !category.isBlank()) {
            cat = categoryService.findByName(category);
        }

        List<Book> books = bookService.filterBooks(search, cat);

        List<BookResponse> responses = books.stream()
                .map(BookResponse::new)
                .toList();

        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responses);
    }



}
