package pl.fula.bookstore.bookstore;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.fula.bookstore.bookstore.catalog.application.CatalogController;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

import java.util.List;

@Component
public class AppInit implements CommandLineRunner {
    private final CatalogController catalogController;
    private final String title;
    private final Long limit;

    // TODO 12 - properties. (values in application.properties)
    //          ':' after properties is a default value (if proprty does not exist)
    public AppInit(CatalogController catalogController, @Value("${bookstore.catalog.booktitle}") String title,
                   @Value("${bookstore.catalog.limit:2}") Long limit) {
        this.catalogController = catalogController;
        this.title = title;
        this.limit = limit;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Book> books = catalogController.findByTitle(title);
        books.stream().limit(limit).forEach(System.out::println);
    }
}
