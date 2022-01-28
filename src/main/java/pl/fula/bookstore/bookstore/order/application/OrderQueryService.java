package pl.fula.bookstore.bookstore.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fula.bookstore.bookstore.order.application.port.OrderUseCase;
import pl.fula.bookstore.bookstore.order.db.OrderJpaRepository;
import pl.fula.bookstore.bookstore.order.domain.Order;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class OrderQueryService implements OrderUseCase {
    private final OrderJpaRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void removeById(Long id) {
        orderRepository.deleteById(id);
    }
}
