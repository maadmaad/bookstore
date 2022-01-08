package pl.fula.bookstore.bookstore.order.application.port;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import pl.fula.bookstore.bookstore.order.domain.OrderItem;
import pl.fula.bookstore.bookstore.order.domain.Recipient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface PlaceOrderUseCase {
    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    @Value
    @Builder
    class PlaceOrderCommand {
        @Singular
        List<OrderItem> items;
        Recipient recipient;
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
