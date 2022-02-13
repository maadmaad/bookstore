package pl.fula.bookstore.bookstore.order.web;

import lombok.AllArgsConstructor;
import lombok.Data;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase;
import pl.fula.bookstore.bookstore.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import pl.fula.bookstore.bookstore.order.application.port.OrderUseCase;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Validated                                                                                                              // TODO - enables validation for @PathVariable and @RequestParam in our controller
                                                                                                                        //        without it
                                                                                                                        //            updateOrder(@PathVariable @Positive Long id)
                                                                                                                        //        will not be validated
public class OrdersController {
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
    public ResponseEntity<Void> newOrder(@Valid @RequestBody PlaceOrderCommand command) {
        manipulateOrderUseCase.placeOrder(command);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(uri).build();
    }

//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public void updateOrderStatus(@PathVariable @Positive Long id, @RequestParam(value = "status") OrderStatus status) {
//        UpdateStatusCommand orderStatusCommand = new UpdateStatusCommand(id, status);
//        ManipulateOrderResponse response = manipulateOrderUseCase.changeOrderStatus(orderStatusCommand);
//
//        if (!response.isSuccess()) {
//            String message = String.join(", ", response.getErrors());
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
//        }
//    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrderStatus(@PathVariable @Positive Long id, @Valid @RequestBody UpdateStatusRestCommand command) {
        OrderStatus orderStatus = OrderStatus
                .parseString(command.getStatus())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Unknown status " + command.getStatus()));

        manipulateOrderUseCase.updateOrderStatus(id, orderStatus);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrder(@PathVariable Long id) {
        orderUseCase.removeById(id);
    }

    @Data
    private static class UpdateStatusRestCommand {
        @NotBlank
        String status;
    }


}
