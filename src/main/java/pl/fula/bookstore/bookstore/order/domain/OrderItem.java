package pl.fula.bookstore.bookstore.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

    private int quantity;

    public OrderItem(Long bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
