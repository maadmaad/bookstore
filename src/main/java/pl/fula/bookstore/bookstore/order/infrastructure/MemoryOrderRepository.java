package pl.fula.bookstore.bookstore.order.infrastructure;

import org.springframework.stereotype.Repository;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderRepository;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryOrderRepository implements OrderRepository {
    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong NEXT_ID = new AtomicLong(0L);

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(nextId());
            order.setCreatedAt(LocalDateTime.now());
        }

        return orders.put(order.getId(), order);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    private long nextId() {
        return NEXT_ID.incrementAndGet();
    }
}