package com.htd.bookstore.service;

import com.htd.bookstore.model.Book;
import com.htd.bookstore.model.CartItem;
import com.htd.bookstore.model.ShoppingCart;
import com.htd.bookstore.model.User;
import com.htd.bookstore.repository.BookRepository;
import com.htd.bookstore.repository.CartItemRepository;
import com.htd.bookstore.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    public CartService(ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository, BookRepository bookRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookRepository = bookRepository;

    }

    /**
     * Adds Book to the cart of the user
     * @param user
     * @param bookId
     * @param quantity
     * @return CartItem
     */
    @Transactional
    public CartItem addBookToCart(User user, Long bookId, int quantity) {
        ShoppingCart shoppingCart = getCartByUser(user);
        Book book = bookRepository.getBooksByBookId(bookId);
        /*
        cases:
        1. item already in cart, then we should just add the quantity
        2. item not yet in cart
        3. item stock not available
         */
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndBook(shoppingCart, book);
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            int newQuantity = cartItem.getQuantity();
            if(newQuantity > book.getStock()) {
                throw new IllegalStateException("Not enough stock on " + book.getTitle());
            }
            return cartItemRepository.save(cartItem);
        }
        if(quantity > book.getStock()) {
            throw new IllegalStateException("Not enough stock on " + book.getTitle());
        }
        CartItem item = new CartItem();
        item.setBook(book);
        item.setQuantity(quantity);
        item.setCart(shoppingCart);


        return cartItemRepository.save(item);
    }

    /**
     * Get the users cart if it exists otherwise make a new one
     * @param user
     * @return ShoppingCart
     */
    public ShoppingCart getCartByUser(User user) {
        return shoppingCartRepository.findByUser(user).orElseGet(() -> {
            ShoppingCart cart = new ShoppingCart();
            cart.setUser(user);
            return shoppingCartRepository.save(cart);
        });
    }

    /**
     * Gets cart items of a user
     * @param user
     * @return List<CartItem> of current user
     */
    public List<CartItem> getCartItems(User user) {
        ShoppingCart cart = getCartByUser(user);
        return cart.getItems();
    }

    /**
     * Delete all items in the users cart
     * @param user
     */
    @Transactional
    public void clearCart(User user) {
        ShoppingCart cart = getCartByUser(user);
        cartItemRepository.deleteByCart(cart);

    }
}
