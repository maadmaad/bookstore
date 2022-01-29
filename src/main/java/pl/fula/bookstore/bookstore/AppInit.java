package pl.fula.bookstore.bookstore;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.fula.bookstore.bookstore.catalog.db.AuthorJpaRepository;
import pl.fula.bookstore.bookstore.catalog.domain.Author;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.order.application.port.OrderUseCase;
import pl.fula.bookstore.bookstore.order.application.port.PlaceOrderUseCase;
import pl.fula.bookstore.bookstore.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import pl.fula.bookstore.bookstore.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderItem;
import pl.fula.bookstore.bookstore.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Component
public class AppInit implements CommandLineRunner {
    private final CatalogUseCase catalogUseCase;
    private final PlaceOrderUseCase placeOrderUseCase;
    private final OrderUseCase orderUseCase;
    private final AuthorJpaRepository authorRepository;

    @Override
    public void run(String... args) throws Exception {
        initData();
        placeOrder();
    }

    private void placeOrder() {
        Book book1 = catalogUseCase.findOneByTitle("efFect").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book book2 = catalogUseCase.findOneByTitle("uZZler").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Recipient recipient = Recipient.builder()
                .name("Mariusz")
                .phone("555-444-333")
                .street("Owocowa")
                .city("Kraków")
                .zipCode("30-600")
                .email("mariusz@gmail.com")
                .build();

        PlaceOrderCommand command = PlaceOrderCommand.builder()
                .recipient(recipient)
                .item(new OrderItem(book1.getId(), 11))
                .item(new OrderItem(book2.getId(), 22))
                .build();

        PlaceOrderResponse response = placeOrderUseCase.placeOrder(command);

        List<Order> orders = orderUseCase.findAll();
    }

    private void initData() {
        Author joshua = new Author("Joshua", "Bloch");
        Author neal = new Author("Neal", "Gafter");
        authorRepository.save(joshua);
        authorRepository.save(neal);

        CreateBookCommand effectiveJava = new CreateBookCommand(
                "Effective Java",
                Set.of(joshua.getId()),
                2005,
                BigDecimal.valueOf(79.00));
        CreateBookCommand javaPuzzlers = new CreateBookCommand(
                "Java Puzzlers", Set.of(joshua.getId(), neal.getId()),
                2018,
                BigDecimal.valueOf(99.00));

        catalogUseCase.addBook(javaPuzzlers);
        catalogUseCase.addBook(effectiveJava);
    }
}
