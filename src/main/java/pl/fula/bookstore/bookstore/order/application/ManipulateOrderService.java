package pl.fula.bookstore.bookstore.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase;
import pl.fula.bookstore.bookstore.order.db.OrderJpaRepository;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
class ManipulateOrderService implements ManipulateOrderUseCase {
    private final OrderJpaRepository orderRepository;

//    @Override
//    public ManipulateOrderResponse updateOrderStatus(UpdateStatusCommand command) {
//        return orderRepository.findById(command.getOrderId())
//                .map(o -> {
//                    o.setStatus(command.getStatus());
//                    orderRepository.save(o);
//                    return ManipulateOrderResponse.SUCCESS;
//                })
//                .orElseGet(() -> {
//                    String errorMsg = "Order with id: " + command.getOrderId() + " not found";
//                    return new ManipulateOrderResponse(false, List.of(errorMsg));
//                });
//    }

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order.builder()
                .recipient(command.getRecipient())
                .items(command.getItems())
                .build();
        orderRepository.save(order);

        return PlaceOrderResponse.success(order.getId());
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) {
        orderRepository.findById(id).
                ifPresent(order -> {
                    order.updateStatus(status);
                    orderRepository.save(order);
                });
    }
}
