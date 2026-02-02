package com.htd.bookstore.dto;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Book response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long bookId;
    private String title;
    private String author;
    private String description;
    private BigDecimal price;
    private int stock;
    private Long categoryId;
    private String categoryName;

    /**
     * Instantiates a new Book response.
     *
     * @param book the book
     */
    public BookResponse(Book book) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.price = book.getPrice();
        this.stock = book.getStock();
        this.categoryId = book.getCategory().getCategoryId();
        this.categoryName = book.getCategory().getName();

    }
}
