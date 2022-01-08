package pl.fula.bookstore.bookstore.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fula.bookstore.bookstore.order.application.port.QueryOrderUseCase;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
class QueryOrderService implements QueryOrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
