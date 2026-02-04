package com.htd.bookstore.controller;

import com.htd.bookstore.dto.BookResponse;
import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import com.htd.bookstore.service.BookService;
import com.htd.bookstore.service.CategoryService;
import com.htd.bookstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
class AdminController {
    private final BookService bookService;
    private final CategoryService categoryService;

    public AdminController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody Map<String, String> payload) {
        Category category = categoryService.findByName(payload.get("category"));

        Book book = new Book();
        book.setTitle(payload.get("title"));
        book.setAuthor(payload.get("author"));
        book.setDescription(payload.get("description"));
        book.setPrice(new BigDecimal(payload.get("price")));
        book.setStock(Integer.parseInt(payload.get("stock")));
        if (category == null) {
            category = new Category();
            category.setName(payload.get("category"));
            category = categoryService.addCategory(category);
        }
        book.setCategory(category);

        Book saved = bookService.addBook(book);
        BookResponse bookResponse = new BookResponse(saved);

        return ResponseEntity.ok(bookResponse);
    }
}
