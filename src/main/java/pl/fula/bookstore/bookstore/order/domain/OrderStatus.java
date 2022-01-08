package pl.fula.bookstore.bookstore.order.domain;

public enum OrderStatus {
    NEW,
    CONFIRMED,
    IN_DELIVERY,
    DELIVERED,
    CANCELED,
    RETURNED;
}
