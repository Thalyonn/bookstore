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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Add book item that isn't in the cart yet.")
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
    @DisplayName("Add book item that isn't in stock.")
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

        //assert the proper error message
        assertEquals("Not enough stock on " + book.getTitle(), exception.getMessage());
        //verify cartItem isn't saved
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    @DisplayName("Get cart by user when cart exists.")
    void getCartShouldReturnExistingCartWhenFound() {
        User user = new User();
        user.setUsername("Fumbles");

        ShoppingCart existingCart = new ShoppingCart();
        existingCart.setUser(user);

        when(shoppingCartRepository.findByUser(user)).thenReturn(Optional.of(existingCart));

        ShoppingCart result = cartService.getCartByUser(user);
        //same cart returned
        assertSame(existingCart, result);
        //save should not be called
        verify(shoppingCartRepository, never()).save(any());
    }

    @Test
    @DisplayName("Get cart by user should create new cart when not found.")
    void getCartShouldCreateNewCartWhenNotFound() {
        User user = new User();
        user.setUsername("Fumbles");

        ShoppingCart newCart = new ShoppingCart();
        newCart.setUser(user);

        //when cart isn't found return empty
        when(shoppingCartRepository.findByUser(user)).thenReturn(Optional.empty());
        //return the cart when adding shopping cart to repository
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(newCart);

        ShoppingCart result = cartService.getCartByUser(user);
        //assert cart exists
        assertNotNull(result);
        //assert cart is owned by the user
        assertEquals(user, result.getUser());
        verify(shoppingCartRepository).save(any(ShoppingCart.class)); //save should be called once
    }

    @Test
    @DisplayName("Get users' cart items.")
    void returnCartItemsForUser() {
        //set objects to be used for cartService.getCartItems(User)
        User user = new User();
        user.setUsername("Fumbles");

        ShoppingCart cart = new ShoppingCart();
        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem();
        cart.setItems(Arrays.asList(item1, item2));

        //stub for when shoppingCartRepository finds the user's cart
        when(shoppingCartRepository.findByUser(user)).thenReturn(java.util.Optional.of(cart));

        List<CartItem> result = cartService.getCartItems(user);
        //the two cart items should be in the cart
        assertEquals(2, result.size());
        assertTrue(result.contains(item1));
        assertTrue(result.contains(item2));
    }

    @Test
    @DisplayName("Clear users' cart.")
    void clearUsersCart() {
        //create user and object to call clear cart
        User user = new User();
        user.setUsername("Fumbles");
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        when(shoppingCartRepository.findByUser(user)).thenReturn(java.util.Optional.of(cart));

        cartService.clearCart(user);

        //verify deleteByCart called with the user's cart
        verify(cartItemRepository).deleteByCart(cart);
    }
}
