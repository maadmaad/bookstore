package pl.fula.bookstore.bookstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AppInit implements CommandLineRunner {
    private final CatalogUseCase catalog;
    private final String title;
    private final String author;
    private final Long limit;

    // TODO 12 - properties. (values in application.properties)
    //          ':' after properties is a default value (if proprty does not exist)
    public AppInit(CatalogUseCase catalogController, @Value("${bookstore.catalog.booktitle}") String title,
                   @Value("${bookstore.catalog.limit:2}") Long limit,
                   @Value("${bookstore.catalog.author}") String author) {
        this.catalog = catalogController;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
        findByTitle();
        findAndUpdate();
        findByTitle();

//        findByAuthor();
    }

    private void findAndUpdate() {
        System.out.println("Updating book ...");
        catalog.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(b -> {
                    UpdateBookCommand command = UpdateBookCommand.builder()
                        .id(b.getId())
                        .newTitle("Pan Tadeusz, czyli ostatni zajazd na Litwie")
                        .build();
                    UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updated book success: " + response.isSuccess());
                });
    }

    private void initData() {
        catalog.addBook(new CreateBookCommand("Harry Potter i Komnata Tajemnic", "JK Rowling", 1998, BigDecimal.valueOf(10.0)));
        catalog.addBook(new CreateBookCommand("Władca Pierścieni: Dwie Wieże", "JPR Tolkien", 1954, BigDecimal.valueOf(10.0)));
        catalog.addBook(new CreateBookCommand("Mężczyźni, którzy nienawidzą kobiet", "Stieg Larsson", 2005, BigDecimal.valueOf(10.0)));
        catalog.addBook(new CreateBookCommand("Sezon Burz", "Andrzej Sapkowski", 2013, BigDecimal.valueOf(10.0)));
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1901, BigDecimal.valueOf(10.0)));
        catalog.addBook(new CreateBookCommand("Ogniem i Mieczem", "Henryk Sienkiewicz", 1902, BigDecimal.valueOf(10.0)));
        catalog.addBook(new CreateBookCommand("Chłopi", "Adam Mickiewicz", 1904, BigDecimal.valueOf(10.0)));
        catalog.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1905, BigDecimal.valueOf(10.0)));
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        // With limit:
//        books.stream().limit(limit).forEach(System.out::println);
        books.stream().forEach(System.out::println);
    }

    private void findByAuthor() {
        catalog.findByAuthor(author).stream().limit(limit).forEach(System.out::println);
    }
}
