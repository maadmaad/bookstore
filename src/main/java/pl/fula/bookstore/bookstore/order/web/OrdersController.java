package pl.fula.bookstore.bookstore.order.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase.ManipulateOrderResponse;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase.UpdateOrderStatusCommand;
import pl.fula.bookstore.bookstore.order.application.port.OrderUseCase;
import pl.fula.bookstore.bookstore.order.application.port.PlaceOrderUseCase;
import pl.fula.bookstore.bookstore.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderItem;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;
import pl.fula.bookstore.bookstore.order.domain.Recipient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Validated                                                                                                              // TODO - enables validation for @PathVariable and @RequestParam in our controller
                                                                                                                        //        without it
                                                                                                                        //            updateOrder(@PathVariable @Positive Long id)
                                                                                                                        //        will not be validated
public class OrdersController {
    private final PlaceOrderUseCase placeOrderUseCase;
    private final ManipulateOrderUseCase manipulateOrderUseCase;
    private final OrderUseCase orderUseCase;
    private final CatalogUseCase catalog;

    @GetMapping
    public ResponseEntity<List<Order>> findAllOrders() {
        return ResponseEntity.ok(orderUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long id) {
        return orderUseCase.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
//        return orderUseCase.findById(id)
//                .map(o -> ResponseEntity.ok(o))
//                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> newOrder(@Valid @RequestBody PlaceOrderRestCommand command) {
        placeOrderUseCase.placeOrder(command.toPlaceOrderCommand());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrder(@PathVariable @Positive Long id, @RequestParam(value = "status") OrderStatus status) {
        UpdateOrderStatusCommand orderStatusCommand = new UpdateOrderStatusCommand(id, status);
        ManipulateOrderResponse response = manipulateOrderUseCase.changeOrderStatus(orderStatusCommand);

        if (!response.isSuccess()) {
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrder(@PathVariable Long id) {
        orderUseCase.removeById(id);
    }

    @Data
    private static class PlaceOrderRestCommand {
        @Valid
        @NotNull
        RecipientCommand recipientCommand;

        @Valid
        @NotEmpty
        List<OrderItemCommand> orderItems;

        PlaceOrderCommand toPlaceOrderCommand() {
            return PlaceOrderCommand.builder()
                    .recipient(recipientCommand.toRecipient())
                    .items(orderItems.stream().map(OrderItemCommand::toOrderItem).collect(Collectors.toList()))
                    .build();
        }
    }

    @Value
    private static class RecipientCommand {
        @NotBlank
        String name;

        String phone;
        String street;
        String city;
        String zipCode;
        String email;

        Recipient toRecipient() {
            return new Recipient(name, phone, street, city, zipCode, email);
        }
    }

    @Value
    private static class OrderItemCommand {
        @NotNull
        @Positive
        Long bookId;

        @NotNull
        @Positive
        int quantity;

        public OrderItem toOrderItem() {
            return new OrderItem(bookId, quantity);
        }
    }
}
