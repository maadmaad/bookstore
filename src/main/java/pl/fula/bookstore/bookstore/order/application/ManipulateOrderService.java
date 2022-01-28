package pl.fula.bookstore.bookstore.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase;
import pl.fula.bookstore.bookstore.order.db.OrderJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
class ManipulateOrderService implements ManipulateOrderUseCase {
    private final OrderJpaRepository orderRepository;

    @Override
    public ManipulateOrderResponse changeOrderStatus(UpdateOrderStatusCommand command) {
        return orderRepository.findById(command.getOrderId())
                .map(o -> {
                    o.setStatus(command.getStatus());
                    orderRepository.save(o);
                    return ManipulateOrderResponse.SUCCESS;
                })
                .orElseGet(() -> {
                    String errorMsg = "Order with id: " + command.getOrderId() + " not found";
                    return new ManipulateOrderResponse(false, List.of(errorMsg));
                });
    }
}
