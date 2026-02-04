package com.htd.bookstore.controller;

import com.htd.bookstore.dto.BookResponse;
import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import com.htd.bookstore.service.BookService;
import com.htd.bookstore.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
     * @param page the page number
     * @param size the size to return
     * @param sortBy the value to sort by
     * @return the response entity
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<BookResponse>> findByCategory(@PathVariable String category,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10")int size,
                                                             @RequestParam(defaultValue = "title") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> books = bookService.getBooksByCategory(categoryService.findByName(category), pageable);
        if(books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            Page<BookResponse> responses = books.map(BookResponse::new);
            return ResponseEntity.ok(responses);
        }
    }

    /**
     * Find by title response entity of BookResponses.
     *
     * @param title the title
     * @param page the page number
     * @param size the size to return
     * @param sortBy the value to sort by
     * @return the response entity
     */
    @GetMapping("/search/{title}")
    public ResponseEntity<Page<BookResponse>> findByTitle(@PathVariable String title,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10")int size,
                                                          @RequestParam(defaultValue = "title") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> books = bookService.searchBooks(title, pageable);
        if(books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            Page<BookResponse> responses = books.map(BookResponse::new);
            return ResponseEntity.ok(responses);
        }
    }


    /**
     * Filter books by search keyword and/or category.
     *
     * @param search   optional search keyword
     * @param category optional category name
     * @param page the page number
     * @param size the size to return
     * @param sortBy the value to sort by
     * @return list of book responses
     */
    @GetMapping("/filter")
    public ResponseEntity<Page<BookResponse>> getBooks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Category cat = null;
        if (category != null && !category.isBlank()) {
            cat = categoryService.findByName(category);
        }

        Page<Book> books = bookService.filterBooks(search, cat, pageable);
        Page<BookResponse> responses = books.map(BookResponse::new);


        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responses);
    }



}
