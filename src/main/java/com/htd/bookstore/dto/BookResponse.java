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
    private Category category;

    public BookResponse(Book book) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.price = book.getPrice();
        this.stock = book.getStock();

    }
}
