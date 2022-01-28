package pl.fula.bookstore.bookstore.order.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fula.bookstore.bookstore.order.domain.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
