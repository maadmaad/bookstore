package pl.fula.bookstore.bookstore.order.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {
    private Long id;
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;
    private List<OrderItem> items;
    private Recipient recipient;
    private LocalDateTime createdAt;

//    public BigDecimal totalPrice() {
//        return items.stream()
//                .map(item -> item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
}
