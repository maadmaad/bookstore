package pl.fula.bookstore.bookstore.order.application.port;

import pl.fula.bookstore.bookstore.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {
    List<Order> findAll();
}
