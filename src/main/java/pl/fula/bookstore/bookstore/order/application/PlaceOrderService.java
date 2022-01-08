package pl.fula.bookstore.bookstore.order.application;

import lombok.RequiredArgsConstructor;
import pl.fula.bookstore.bookstore.order.application.port.PlaceOrderUseCase;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderRepository;

@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order.builder()
                .recipient(command.getRecipient())
                .items(command.getItems())
                .build();
        orderRepository.save(order);

        return PlaceOrderResponse.success(order.getId());
    }
}
