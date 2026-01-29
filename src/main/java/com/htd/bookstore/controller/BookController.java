package com.htd.bookstore.controller;

import com.htd.bookstore.dto.BookResponse;
import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import com.htd.bookstore.service.BookService;
import com.htd.bookstore.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    BookService bookService;
    CategoryService categoryService;
    public BookController(BookService bookService,  CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll() {
        List<Book> books = bookService.getAllBooks();
        List<BookResponse> responses = books.stream().map(BookResponse::new)
                .toList();
        return ResponseEntity.ok(responses);
    }

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

}
