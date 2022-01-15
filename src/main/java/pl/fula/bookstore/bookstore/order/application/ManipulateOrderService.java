package pl.fula.bookstore.bookstore.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderRepository;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class ManipulateOrderService implements ManipulateOrderUseCase {
    private final OrderRepository orderRepository;

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
