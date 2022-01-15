package pl.fula.bookstore.bookstore.order.domain;

import java.util.Arrays;

public enum OrderStatus {
    NEW("NEW"),
    CONFIRMED("CONFIRMED"),
    IN_DELIVERY("IN_DELIVERY"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED"),
    RETURNED("RETURNED");

    String value;

    OrderStatus(String value) {
        this.value = value.toUpperCase();
    }

    public String getValue() {
        return this.value;
    }

    public static boolean isValidStatus(String statusToCheck) {
        return Arrays.stream(OrderStatus.values())
                .map(OrderStatus::getValue)
                .anyMatch(s -> s.equals(statusToCheck.toUpperCase()));
    }
}
