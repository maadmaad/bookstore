package pl.fula.bookstore.bookstore.order.application.port;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import pl.fula.bookstore.bookstore.order.domain.OrderItem;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;
import pl.fula.bookstore.bookstore.order.domain.Recipient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface ManipulateOrderUseCase {

    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    void deleteOrderById(Long id);
    void updateOrderStatus(Long id, OrderStatus status);

    @Value
    @Builder
    class PlaceOrderCommand {
        @Singular
        List<OrderItem> items;
        Recipient recipient;
    }

    @Value
    class UpdateStatusCommand {
        Long orderId;
        OrderStatus status;
    }

    @Value
    class PlaceOrderResponse {
        boolean success;
        Long orderId;
        List<String> errors;

        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, orderId, Collections.emptyList());
        }

        public static PlaceOrderResponse failure(String... errors) {
            return new PlaceOrderResponse(false, null, Arrays.asList(errors));
        }
    }
}
