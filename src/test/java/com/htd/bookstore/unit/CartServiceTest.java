package com.htd.bookstore.unit;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.CartItem;
import com.htd.bookstore.model.ShoppingCart;
import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.BookRepository;
import com.htd.bookstore.repository.CartItemRepository;
import com.htd.bookstore.repository.ShoppingCartRepository;
import com.htd.bookstore.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    ShoppingCartRepository shoppingCartRepository;
    @Mock
    CartItemRepository cartItemRepository;
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    CartService cartService;

    @Test
    @DisplayName("Add book to cart while the same item already is in cart, should add the quantity to existing item quantity")
    void addBookItemExistsAddQuantity() {
        //initialize items
        ShoppingCart cart = new ShoppingCart();
        User user = new User();
        user.setUsername("Fumbles");
        cart.setUser(user);
        Book book = new Book();
        book.setTitle("The Hunger Games");
        book.setAuthor("Suzy");
        book.setStock(10);
        //make an item that is already in cart
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setCart(cart);
        cartItem.setQuantity(2);

        when(bookRepository.getBooksByBookId(1L)).thenReturn(book);
        //return an optional of cartItem as it already is in the cart of the user
        when(cartItemRepository.findByCartAndBook(cart, book)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(any(CartItem.class))).thenAnswer(inv -> inv.getArgument(0));
        when(cartService.getCartByUser(user)).thenReturn(cart);

        //act
        CartItem result = cartService.addBookToCart(user, 1L, 3);

        //assert that the quantity is 3 + existing item original value
        assertEquals(5, result.getQuantity());
        //verify that save is called
        verify(cartItemRepository).save(cartItem);

    }

    @Test
    void addBookItemNotYetInCartSuccess() {
        //create objects to be used in cartService.addBookToCart call
        User user = new User();
        user.setUsername("Fumbles");

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        Book book = new Book();
        book.setBookId(2L);
        book.setTitle("Catching Fire");
        book.setAuthor("Suzy");
        book.setStock(10);


        when(bookRepository.getBooksByBookId(2L)).thenReturn(book);
        //return Optional.empty() as item does not exist in users existing cart yet.
        when(cartItemRepository.findByCartAndBook(cart, book)).thenReturn(Optional.empty());
        when(cartItemRepository.save(any(CartItem.class))).thenAnswer(inv -> inv.getArgument(0));
        when(cartService.getCartByUser(user)).thenReturn(cart);

        CartItem result = cartService.addBookToCart(user, 2L, 4);

        //assert
        assertEquals(4, result.getQuantity());
        assertEquals(book, result.getBook());
        //verify that cartItemRepository is saved with result
        verify(cartItemRepository).save(result);
    }

    @Test
    void addBookNoStockFailure() {
        //create objects to be used in cartService.addBookToCart call
        User user = new User();
        user.setUsername("Fumbles");

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        //book has 2 stock
        Book book = new Book();
        book.setBookId(3L);
        book.setTitle("Mockingjay");
        book.setAuthor("Suzy");
        book.setStock(2);

        when(bookRepository.getBooksByBookId(3L)).thenReturn(book);
        when(cartItemRepository.findByCartAndBook(cart, book)).thenReturn(Optional.empty());
        when(cartService.getCartByUser(user)).thenReturn(cart);

        //we are trying to order 5 books but book only has 2 stock
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> cartService.addBookToCart(user, 3L, 5)
        );

        //asserts
        assertEquals("Not enough stock on " + book.getTitle(), exception.getMessage());
        //verify cartItem isn't saved
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }
}
