package pl.fula.bookstore.bookstore.order.domain;

import lombok.Value;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

@Value
public class OrderItem {
    Book book;
    int quantity;
}
