package pl.fula.bookstore.bookstore.order.application.port;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;

import java.util.Collections;
import java.util.List;

public interface ManipulateOrderUseCase {
    ManipulateOrderResponse changeOrderStatus(UpdateOrderStatusCommand command);

    @Value
    @AllArgsConstructor
    class UpdateOrderStatusCommand {
        Long orderId;
        OrderStatus status;
    }

    @Value
    class ManipulateOrderResponse {
        public static ManipulateOrderResponse SUCCESS = new ManipulateOrderResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }
}
