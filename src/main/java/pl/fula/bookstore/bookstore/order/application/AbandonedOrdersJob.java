package pl.fula.bookstore.bookstore.order.application;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase;
import pl.fula.bookstore.bookstore.order.db.OrderJpaRepository;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class AbandonedOrdersJob {
    private final OrderJpaRepository orderRepository;
    private final ManipulateOrderUseCase orderUseCase;

    @Scheduled(fixedRate = 60_000)
    @Transactional
    public void run() {
        LocalDateTime timestamp = LocalDateTime.now().minusMinutes(5);
        List<Order> orders = orderRepository.findByStatusAndCreatedAtLessThanEqual(OrderStatus.NEW, timestamp);
        System.out.println("Found orders to be abandoned: " + orders.size());
        orders.forEach(order -> orderUseCase.updateOrderStatus(order.getId(), OrderStatus.ABANDONED));
        // find abandoned orders
        // update status as abandoned

    }
}
