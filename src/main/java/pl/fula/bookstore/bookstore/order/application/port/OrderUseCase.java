package pl.fula.bookstore.bookstore.order.application.port;

import pl.fula.bookstore.bookstore.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderUseCase {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    void removeById(Long id);
}
