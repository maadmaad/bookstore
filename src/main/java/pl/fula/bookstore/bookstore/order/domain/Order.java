package pl.fula.bookstore.bookstore.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.fula.bookstore.bookstore.jpa.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order extends BaseEntity {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private  OrderStatus status = OrderStatus.NEW;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Recipient recipient;

    private LocalDateTime createdAt_1;

    @CreatedDate
    private LocalDateTime createdAt_2;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PreUpdate
    private void updatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    private void createdAt_1() {
        this.createdAt_1 = LocalDateTime.now();
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = this.status.updateStatus(newStatus);
    }
}
