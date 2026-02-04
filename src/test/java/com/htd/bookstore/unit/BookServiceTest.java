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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    @DisplayName("Search book by Keyword")
    void getsBookByKeywordReturnsABook() {
        Book book = new Book();
        book.setTitle("The Hunger Games");

        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.findByTitleContainingIgnoreCase("hunger", pageable))
                .thenReturn(new PageImpl<>(List.of(book), pageable, 1));

        Page<Book> result = bookService.searchBooks("hunger", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("The Hunger Games", result.getContent().get(0).getTitle());
        verify(bookRepository).findByTitleContainingIgnoreCase("hunger", pageable);
    }


    @Test
    @DisplayName("Get books by category")
    void getsBooksByCategoryReturnsBooks() {
        Category category = new Category();
        category.setName("Fiction");

        Book book = new Book();
        book.setTitle("Catching Fire");
        book.setCategory(category);

        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.findByCategory(category, pageable))
                .thenReturn(new PageImpl<>(List.of(book), pageable, 1));

        Page<Book> result = bookService.getBooksByCategory(category, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Catching Fire", result.getContent().get(0).getTitle());
        verify(bookRepository).findByCategory(category, pageable);
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

    @Test
    @DisplayName("Filter books with both keyword and category")
    void filterBooksKeywordAndCategory() {
        Category category = new Category();
        category.setName("Fiction");

        Book book = new Book();
        book.setTitle("Catching Fire");
        book.setCategory(category);

        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.findByTitleContainingIgnoreCaseAndCategory("fire", category, pageable))
                .thenReturn(new PageImpl<>(List.of(book), pageable, 1));
        //filter books by title keyword fire and the category
        Page<Book> result = bookService.filterBooks("fire", category, pageable);
        //should return by keyword and category
        assertEquals(1, result.getTotalElements());
        assertEquals("Catching Fire", result.getContent().get(0).getTitle());
        verify(bookRepository).findByTitleContainingIgnoreCaseAndCategory("fire", category, pageable);
    }



    @Test
    @DisplayName("Filter books with only keyword")
    void filterBooksKeywordOnly() {
        Book book = new Book();
        book.setTitle("Mockingjay");
        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.findByTitleContainingIgnoreCase("mock", pageable))
                .thenReturn(new PageImpl<>(List.of(book), pageable, 1));
        //filter books with keyword only
        Page<Book> result = bookService.filterBooks("mock", null, pageable);
        //should return the book with matching keyword
        assertEquals(1, result.getTotalElements());
        assertEquals("Mockingjay", result.getContent().get(0).getTitle());
        verify(bookRepository).findByTitleContainingIgnoreCase("mock", pageable);
    }

    @Test
    @DisplayName("Filter books with only category")
    void filterBooksCategoryOnly() {
        Category category = new Category();
        category.setName("Non-Fiction");

        Book book = new Book();
        book.setTitle("Educated");
        book.setCategory(category);
        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.findByCategory(category, pageable)).thenReturn(new PageImpl<>(List.of(book), pageable, 1));
        //filter books with category only
        Page<Book> result = bookService.filterBooks(null, category, pageable);
        //should return the book with matching category
        assertEquals(1, result.getTotalElements());
        assertEquals("Educated", result.getContent().get(0).getTitle());
        verify(bookRepository).findByCategory(category, pageable);
    }

    @Test
    @DisplayName("Filter books with no keyword and no category")
    void filterBooksNoKeywordNoCategory() {
        Book book1 = new Book();
        book1.setTitle("Book One");
        Book book2 = new Book();
        book2.setTitle("Book Two");
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = Arrays.asList(book1, book2);
        Page<Book> page = new PageImpl<>(books, pageable, books.size());
        when(bookRepository.findAll(pageable)).thenReturn(page);
        //filter books with no keyword and no category
        Page<Book> result = bookService.filterBooks(null, null, pageable);
        //should return all books
        assertEquals(2, result.getTotalElements());
        verify(bookRepository).findAll(pageable);
    }

}
