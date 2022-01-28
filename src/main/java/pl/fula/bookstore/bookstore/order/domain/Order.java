package pl.fula.bookstore.bookstore.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    private  OrderStatus status = OrderStatus.NEW;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    private transient Recipient recipient;

    private LocalDateTime createdAt;

//    public BigDecimal totalPrice() {
//        return items.stream()
//                .map(item -> item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
    }
}
