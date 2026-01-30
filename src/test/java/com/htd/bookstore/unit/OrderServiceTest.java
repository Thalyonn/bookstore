package com.htd.bookstore.unit;

import com.htd.bookstore.model.*;
import com.htd.bookstore.repository.BookRepository;
import com.htd.bookstore.repository.OrderItemRepository;
import com.htd.bookstore.repository.OrderRepository;
import com.htd.bookstore.service.CartService;
import com.htd.bookstore.service.OrderService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CartService cartService;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderService orderService;


    @Test
    void contextLoads() {

    }

    @Test
    void checkoutCartNotEmptySuccess() {
        //when for cartservice getcartitems
        User user = new User();
        user.setUsername("Jakob");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);

        Book book = new Book();
        book.setTitle("The Good Book");
        book.setAuthor("Jason");
        book.setPrice(new BigDecimal(10));
        book.setStock(10);

        Book book2 = new Book();
        book2.setTitle("The Alright Book");
        book2.setAuthor("Jean");
        book2.setPrice(new BigDecimal(5));
        book2.setStock(10);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setCart(shoppingCart);
        cartItem.setBook(book);


        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(2);
        cartItem2.setCart(shoppingCart);
        cartItem2.setBook(book2);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        cartItemList.add(cartItem2);
        when(cartService.getCartItems(user)).thenReturn(cartItemList);

        //same order saved (argument 0)
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        //OrderItem passed
        when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(inv -> inv.getArgument(0));

        Order order = orderService.checkout(user);

        assertNotNull(order);
        assertEquals(order.getUser().getUsername(), user.getUsername());
        //the total price of the books
        assertEquals(new BigDecimal(20), order.getTotalAmount());
        




    }
    @Test
    void checkoutCartEmpty() {

    }

    @Test
    void checkoutCartNotEnoughStock() {

    }
}
