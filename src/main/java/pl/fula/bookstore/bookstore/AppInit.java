package pl.fula.bookstore.bookstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.order.application.port.PlaceOrderUseCase;
import pl.fula.bookstore.bookstore.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import pl.fula.bookstore.bookstore.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import pl.fula.bookstore.bookstore.order.application.port.OrderUseCase;
import pl.fula.bookstore.bookstore.order.domain.Order;
import pl.fula.bookstore.bookstore.order.domain.OrderItem;
import pl.fula.bookstore.bookstore.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AppInit implements CommandLineRunner {
    private final CatalogUseCase catalogUseCase;
    private final PlaceOrderUseCase placeOrderUseCase;
    private final OrderUseCase orderUseCase;
    private final String title;
    private final String author;
    private final Long limit;

    public AppInit(CatalogUseCase catalogUseCase, PlaceOrderUseCase placeOrderUseCase,
                   OrderUseCase orderUseCase, @Value("${bookstore.catalog.booktitle}") String title,
                   @Value("${bookstore.catalog.limit:2}") Long limit,                                                   // TODO 12 - properties. (values in application.properties)
                   @Value("${bookstore.catalog.author}") String author) {                                               //           ':' after properties is a default value (if
        this.catalogUseCase = catalogUseCase;                                                                           //           proprty does not exist)
        this.placeOrderUseCase = placeOrderUseCase;
        this.orderUseCase = orderUseCase;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
        searchCatalog();
        placeOrder();
        placeOrder();
    }

    private void placeOrder() {
        Book book1 = catalogUseCase.findOneByTitle("Pan Tadeusz").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book book2 = catalogUseCase.findOneByTitle("Chłopi").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
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
                .item(new OrderItem(1L, 11))
                .item(new OrderItem(2L, 22))
                .build();

        PlaceOrderResponse response = placeOrderUseCase.placeOrder(command);

        List<Order> orders = orderUseCase.findAll();
    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
    }

    private void findAndUpdate() {
        catalogUseCase.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(b -> {
                    UpdateBookCommand command = UpdateBookCommand.builder()
                            .id(b.getId())
                            .newTitle("Pan Tadeusz, czyli ostatni zajazd na Litwie")
                            .build();
                    UpdateBookResponse response = catalogUseCase.updateBook(command);
                });
    }

    private void initData() {
        catalogUseCase.addBook(new CreateBookCommand("Harry Potter i Komnata Tajemnic", "JK Rowling", 1998, BigDecimal.valueOf(19.99)));
        catalogUseCase.addBook(new CreateBookCommand("Władca Pierścieni: Dwie Wieże", "JPR Tolkien", 1954, BigDecimal.valueOf(10.0)));
        catalogUseCase.addBook(new CreateBookCommand("Mężczyźni, którzy nienawidzą kobiet", "Stieg Larsson", 2005, BigDecimal.valueOf(10.0)));
        catalogUseCase.addBook(new CreateBookCommand("Sezon Burz", "Andrzej Sapkowski", 2013, BigDecimal.valueOf(10.0)));
        catalogUseCase.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1901, BigDecimal.valueOf(10.0)));
        catalogUseCase.addBook(new CreateBookCommand("Ogniem i Mieczem", "Henryk Sienkiewicz", 1902, BigDecimal.valueOf(10.0)));
        catalogUseCase.addBook(new CreateBookCommand("Chłopi", "Adam Mickiewicz", 1904, BigDecimal.valueOf(10.0)));
        catalogUseCase.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1905, BigDecimal.valueOf(10.0)));
    }

    private void findByTitle() {
        List<Book> books = catalogUseCase.findByTitle(title);
        // With limit:
    }
}
