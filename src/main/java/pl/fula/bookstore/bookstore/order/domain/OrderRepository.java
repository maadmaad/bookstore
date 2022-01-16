package pl.fula.bookstore.bookstore.order.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    List<Order> findAll();

    Optional<Order> findById(Long id);

    void removeById(Long id);
}
