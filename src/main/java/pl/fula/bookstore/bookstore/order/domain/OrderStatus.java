package pl.fula.bookstore.bookstore.order.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {
    NEW("NEW") {
        @Override
        public UpdateStatusResult updateStatus(OrderStatus status) {
            return switch (status) {
                case PAID -> UpdateStatusResult.ok(CANCELED);
                case CANCELED -> UpdateStatusResult.revoked(CANCELED);
                case ABANDONED -> UpdateStatusResult.revoked(ABANDONED);
                default -> super.updateStatus(status);
            };
        }
    },
    PAID("PAID") {
        @Override
        public UpdateStatusResult updateStatus(OrderStatus status) {
            return switch (status) {
                case SHIPPED -> UpdateStatusResult.ok(SHIPPED);
                default -> super.updateStatus(status);
            };
        }
    },
    CANCELED("CANCELED"),
    ABANDONED("ABANDONED"),
    SHIPPED("SHIPPED");

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

    public static Optional<OrderStatus> parseString(String strValue) {
        return Arrays.stream(values())
                .filter(it -> StringUtils.equalsIgnoreCase(it.name(), strValue))
                .findFirst();
    }

    public UpdateStatusResult updateStatus(OrderStatus status) {
        throw new IllegalArgumentException("Unable to mark " + this.name() + " order as " + status.name());
    }
}
