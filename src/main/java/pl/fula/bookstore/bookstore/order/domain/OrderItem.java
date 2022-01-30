package pl.fula.bookstore.bookstore.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fula.bookstore.bookstore.jpa.BaseEntity;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItem extends BaseEntity {
    private Long bookId;

    private int quantity;

    public OrderItem(Long bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
