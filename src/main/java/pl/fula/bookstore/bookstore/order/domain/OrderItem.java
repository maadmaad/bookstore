package pl.fula.bookstore.bookstore.order.domain;

import lombok.Value;

@Value
public class OrderItem {
    Long bookId;
    int quantity;
}
