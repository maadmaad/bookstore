package pl.fula.bookstore.bookstore.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fula.bookstore.bookstore.catalog.db.BookJpaRepository;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase;
import pl.fula.bookstore.bookstore.order.db.OrderJpaRepository;
import pl.fula.bookstore.bookstore.order.db.RecipientJpaRepository;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderItem;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;
import pl.fula.bookstore.bookstore.order.domain.Recipient;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ManipulateOrderService implements ManipulateOrderUseCase {
    private final OrderJpaRepository orderRepository;
    private final BookJpaRepository bookRepository;
    private final RecipientJpaRepository recipientRepository;

//    @Override
//    public ManipulateOrderResponse updateOrderStatus(UpdateStatusCommand command) {
//        return orderRepository.findById(command.getOrderId())
//                .map(o -> {
//                    o.setStatus(command.getStatus());
//                    orderRepository.save(o);
//                    return ManipulateOrderResponse.SUCCESS;
//                })
//                .orElseGet(() -> {
//                    String errorMsg = "Order with id: " + command.getOrderId() + " not found";
//                    return new ManipulateOrderResponse(false, List.of(errorMsg));
//                });
//    }

    @Override
    @Transactional
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Set<OrderItem> items = command.getOrderItems()
                .stream()
                .map(this::toOrderItem)
                .collect(Collectors.toSet());

        Order order = Order.builder()
                .recipient(getOrCreateRecipient(command.getRecipient()))
                .items(items)
                .build();
        orderRepository.save(order);
        bookRepository.saveAll(updateBooks(items));

        return PlaceOrderResponse.success(order.getId());
    }

    private Recipient getOrCreateRecipient(Recipient recipient) {
        return recipientRepository
                .findByEmailIgnoreCase(recipient.getEmail())
                .orElse(recipient);
    }

    private Set<Book> updateBooks(Set<OrderItem> items) {
        return items.stream().map(item -> {
            Book book = item.getBook();
            book.setAvailable(book.getAvailable() - item.getQuantity());
            return book;
        }).collect(Collectors.toSet());
    }

    private OrderItem toOrderItem(OrderItemCommand orderItemCommand) {
        Book book = bookRepository.getById(orderItemCommand.getBookId());
        int quantity = orderItemCommand.getQuantity();
        if (book.getAvailable() >= orderItemCommand.getQuantity()) {

            return new OrderItem(book, orderItemCommand.getQuantity());
        }

        throw new IllegalArgumentException("Too many copies of book " + book.getId() + " requested: " + quantity +
                " of " + book.getAvailable() + " available");
    }

    @Override
    @Transactional
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long id, OrderStatus status) {
        orderRepository.findById(id).
                ifPresent(order -> {
                    order.updateStatus(status);
                    orderRepository.save(order);
                });
    }
}
