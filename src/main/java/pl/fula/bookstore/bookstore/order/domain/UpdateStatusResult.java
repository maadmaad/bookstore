package pl.fula.bookstore.bookstore.order.domain;

import lombok.Value;

@Value
public class UpdateStatusResult {
    OrderStatus newStatus;
    boolean revoked;

    static UpdateStatusResult ok(OrderStatus orderStatus) {
        return new UpdateStatusResult(orderStatus, false);
    }

    static UpdateStatusResult revoked(OrderStatus orderStatus) {
        return new UpdateStatusResult(orderStatus, true);
    }
}
