package com.htd.bookstore.dto;

import com.htd.bookstore.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private String username;
    private List<OrderItemResponse> items;
    public OrderResponse(Order order) {
        orderId = order.getOrderId();
        orderDate = order.getOrderDate();
        totalAmount = order.getTotalAmount();
        status = order.getStatus();
        username = order.getUser().getUsername();
        items = order.getItems().stream()
                .map(OrderItemResponse::new)
                .collect(Collectors.toList());

    }
}
