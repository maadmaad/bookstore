package pl.fula.bookstore.bookstore.common.validation;

import org.springframework.core.convert.converter.Converter;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;

import java.util.EnumSet;
import java.util.stream.Collectors;

public class StringToOrderStatusEnumConverter implements Converter<String, OrderStatus> {
    @Override
    public OrderStatus convert(String source) {
        try {
            return OrderStatus.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            String allowedValues = EnumSet.allOf(OrderStatus.class).stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            String message = source.toUpperCase() + " value is not valid. Allowed values: " + allowedValues;
            throw new BookstoreValidationException(message, e);
        }
    }
}
