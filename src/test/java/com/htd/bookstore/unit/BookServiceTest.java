package com.htd.bookstore.unit;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.Category;
import com.htd.bookstore.repository.BookRepository;
import com.htd.bookstore.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Get all books")
    void getAllBooksReturnsAllBooks() {
        Book book1 = new Book();
        book1.setTitle("The Hunger Games");
        Book book2 = new Book();
        book2.setTitle("Catching Fire");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));
        //call getAllBooks
        List<Book> result = bookService.getAllBooks();
        //should return all books
        assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }

    @Test
    @DisplayName("Get a book by id")
    void getsABookByIdReturnsABook() {
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Mockingjay");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        //call getBookById
        Optional<Book> result = bookService.getBookById(1L);
        //there exists a book with id 1L returned
        assertTrue(result.isPresent());
        assertEquals("Mockingjay", result.get().getTitle());
        verify(bookRepository).findById(1L);
    }

    @Test
    void getsBookByKeywordReturnsABook() {
        Book book = new Book();
        book.setTitle("The Hunger Games");

        when(bookRepository.findByTitleContainingIgnoreCase("hunger"))
                .thenReturn(Arrays.asList(book));
        //search book by keyword, hunger games should be found despite casing
        List<Book> result = bookService.searchBooks("hunger");
        //found hunger games book
        assertEquals(1, result.size());
        assertEquals("The Hunger Games", result.getFirst().getTitle());
        verify(bookRepository).findByTitleContainingIgnoreCase("hunger");
    }

    @Test
    @DisplayName("Get books by category")
    void getsBooksByCategoryReturnsBooks() {
        Category category = new Category();
        category.setName("Fiction");

        Book book = new Book();
        book.setTitle("Catching Fire");
        book.setCategory(category);

        when(bookRepository.findByCategory(category)).thenReturn(Arrays.asList(book));
        //get books given a category
        List<Book> result = bookService.getBooksByCategory(category);
        //should return the book with that category
        assertEquals(1, result.size());
        assertEquals("Catching Fire", result.get(0).getTitle());
        verify(bookRepository).findByCategory(category);
    }

    @Test
    @DisplayName("Add a new book")
    void addBook() {
        Book book = new Book();
        book.setTitle("New Book");
        //when calling the book repo save book then return the original book
        when(bookRepository.save(book)).thenReturn(book);
        //call addBook
        Book result = bookService.addBook(book);
        //result should have the New Book title
        assertEquals("New Book", result.getTitle());
        //verify that bookRepository.save(Book) is called
        verify(bookRepository).save(book);
    }

}
